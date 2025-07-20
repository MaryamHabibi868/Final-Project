package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.*;
import ir.maktab.homeservice.domains.enumClasses.*;
import ir.maktab.homeservice.dto.OfferResponse;
import ir.maktab.homeservice.dto.OfferSaveRequest;
import ir.maktab.homeservice.dto.OrderResponse;
import ir.maktab.homeservice.exception.NotApprovedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.exception.NotValidPriceException;
import ir.maktab.homeservice.mapper.OfferMapper;
import ir.maktab.homeservice.mapper.OrderMapper;
import ir.maktab.homeservice.repository.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OfferServiceImplTest {

    private OfferRepository offerRepository;
    private OrderService orderService;
    private SpecialistService specialistService;
    private TransactionService transactionService;
    private WalletService walletService;
    private OfferMapper offerMapper;
    private OrderMapper orderMapper;

    private OfferServiceImpl offerService;

    private Offer offer;
    private Order order;
    private Specialist specialist;
    private Wallet specialistWallet;
    private Wallet customerWallet;
    private Customer customer;

    @BeforeEach
    void setUp() {
        offerRepository = mock(OfferRepository.class);
        orderService = mock(OrderService.class);
        specialistService = mock(SpecialistService.class);
        transactionService = mock(TransactionService.class);
        walletService = mock(WalletService.class);
        offerMapper = mock(OfferMapper.class);
        orderMapper = mock(OrderMapper.class);

        offerService = new OfferServiceImpl(offerRepository, offerMapper, orderService,
                specialistService, transactionService, walletService, orderMapper);

        customerWallet = Wallet.builder().id(1L).balance(BigDecimal.valueOf(500000)).build();
        specialistWallet = Wallet.builder().id(2L).balance(BigDecimal.valueOf(0)).build();

        customer = Customer.builder().id(1L).wallet(customerWallet).build();

        specialist = Specialist.builder()
                .id(1L)
                .wallet(specialistWallet)
                .score(5.0)
                .status(AccountStatus.APPROVED)
                .build();

        order = Order.builder()
                .id(1L)
                .status(OrderStatus.WAITING_FOR_SPECIALIST_OFFER)
                .suggestedPrice(BigDecimal.valueOf(100000))
                .customer(customer)
                .build();

        offer = Offer.builder()
                .id(1L)
                .orderInformation(order)
                .specialist(specialist)
                .status(OfferStatus.DONE)
                .startDateSuggestion(ZonedDateTime.now().minusHours(1))
                .suggestedPrice(BigDecimal.valueOf(100000))
                .taskDuration(Duration.ofHours(2))
                .build();
    }

    @Test
    void testSubmitOfferToOrder_success() {
        OfferSaveRequest request = new OfferSaveRequest(
                BigDecimal.valueOf(100000),
                ZonedDateTime.now().plusHours(2),
                Duration.ofHours(2),
                order.getId(),
                specialist.getId()
        );

        when(orderService.findById(order.getId())).thenReturn(order);
        when(specialistService.findById(specialist.getId())).thenReturn(specialist);
        when(offerRepository.save(any())).thenReturn(offer);
        when(offerMapper.entityMapToResponse(any())).thenReturn(new OfferResponse());

        OfferResponse response = offerService.submitOfferToOrder(request);

        assertNotNull(response);
        verify(offerRepository).save(any());
    }

    @Test
    void testChooseOfferOfSpecialist_success() {
        order.setStatus(OrderStatus.WAITING_FOR_CHOOSING_SPECIALIST);
        offer.setOrderInformation(order);

        when(offerRepository.findById(offer.getId())).thenReturn(Optional.of(offer));
        when(orderService.findById(order.getId())).thenReturn(order);
        when(offerRepository.findAllByOrderInformation_Id(order.getId()))
                .thenReturn(List.of(offer));
        when(offerRepository.saveAll(any())).thenReturn(List.of(offer));
        when(offerMapper.entityMapToResponse(any())).thenReturn(new OfferResponse());

        OfferResponse response = offerService.chooseOfferOfSpecialist(offer.getId());

        assertNotNull(response);
        verify(offerRepository).saveAll(any());
    }

    @Test
    void testStartService_success() {
        order.setStatus(OrderStatus.WAITING_FOR_SPECIALIST_COMING);
        offer.setOrderInformation(order);

        when(offerRepository.findById(offer.getId())).thenReturn(Optional.of(offer));
        when(orderService.findById(order.getId())).thenReturn(order);
        when(offerMapper.entityMapToResponse(any())).thenReturn(new OfferResponse());

        OfferResponse response = offerService.startService(offer.getId());

        assertNotNull(response);
        verify(orderService).save(order);
    }

    @Test
    void testEndService_success() {
        order.setStatus(OrderStatus.HAS_BEGIN);
        offer.setOrderInformation(order);

        when(offerRepository.findById(offer.getId())).thenReturn(Optional.of(offer));
        when(offerRepository.save(any())).thenReturn(offer);
        when(offerMapper.entityMapToResponse(any())).thenReturn(new OfferResponse());

        OfferResponse response = offerService.endService(offer.getId());

        assertNotNull(response);
        verify(orderService).save(order);
    }

    @Test
    void testPaySpecialist_success() {
        order.setStatus(OrderStatus.DONE);
        offer.setStatus(OfferStatus.DONE);
        offer.setOrderInformation(order);
        offer.setSpecialist(specialist);

        when(offerRepository.findById(offer.getId())).thenReturn(Optional.of(offer));
        when(orderService.findById(order.getId())).thenReturn(order);
        when(specialistService.findById(specialist.getId())).thenReturn(specialist);

        offerService.paySpecialist(offer.getId());

        verify(walletService, times(2)).save(any(Wallet.class));
        verify(transactionService, times(2)).save(any(Transaction.class));
        verify(offerRepository).save(offer);
    }

    @Test
    void testFindByOfferOfSpecialistId() {
        Page<Offer> offerPage = new PageImpl<>(List.of(offer));
        when(specialistService.findById(specialist.getId())).thenReturn(specialist);
        when(offerRepository.findAllBySpecialistId(eq(specialist.getId()), any(Pageable.class)))
                .thenReturn(offerPage);
        when(offerMapper.entityMapToResponse(any())).thenReturn(new OfferResponse());

        Page<OfferResponse> result = offerService.findByOfferOfSpecialistId(specialist.getId(), Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testFindAllOffersBySuggestedPrice() {
        Page<Offer> offerPage = new PageImpl<>(List.of(offer));
        when(orderService.findById(order.getId())).thenReturn(order);
        when(offerRepository.findAllByOrderInformation_Id(eq(order.getId()), any(Pageable.class)))
                .thenReturn(offerPage);
        when(offerMapper.entityMapToResponse(any())).thenReturn(new OfferResponse());

        Page<OfferResponse> result = offerService.findAllOffersBySuggestedPrice(order.getId(), Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testFindOrdersBySpecialistId_withFiltering() {
        order.setId(10L);
        offer.setStatus(OfferStatus.DONE);
        offer.setOrderInformation(order);
        specialist.setOffers(Set.of(offer));

        when(specialistService.findById(specialist.getId())).thenReturn(specialist);
        when(offerRepository.findOrdersBySpecialistId(eq(specialist.getId()), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(order)));
        when(orderMapper.entityMapToResponse(order)).thenReturn(new OrderResponse());

        Page<OrderResponse> result = offerService.findOrdersBySpecialistId(specialist.getId(), Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
    }
}
