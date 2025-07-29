package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.*;
import ir.maktab.homeservice.domains.enumClasses.*;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.*;
import ir.maktab.homeservice.mapper.OfferMapper;
import ir.maktab.homeservice.repository.OfferRepository;
import ir.maktab.homeservice.security.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OfferServiceImplTest {

    @Mock private OfferRepository offerRepository;
    @Mock private OfferMapper offerMapper;
    @Mock private OrderService orderService;
    @Mock private SpecialistService specialistService;
    @Mock private TransactionService transactionService;
    @Mock private WalletService walletService;
    @Mock private SecurityUtil securityUtil;

    @InjectMocks private OfferServiceImpl offerService;

    private Offer foundOffer;
    private Order foundOrder;
    private OfferResponse expectedResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        foundOrder = new Order();
        foundOrder.setId(1L);
        foundOrder.setStatus(OrderStatus.WAITING_FOR_CHOOSING_SPECIALIST);

        Specialist specialist = Specialist.builder().id(2L).build();

        foundOffer = new Offer();
        foundOffer.setId(10L);
        foundOffer.setStatus(OfferStatus.PENDING);
        foundOffer.setOrderInformation(foundOrder);
        foundOffer.setSpecialist(specialist);

        expectedResponse = new OfferResponse();
    }

    @Test
    void testSubmitOfferToOrder_Success() {
        OfferSaveRequest req = new OfferSaveRequest();
        req.setOrderId(1L);
        req.setSuggestedPrice(BigDecimal.valueOf(1000));
        req.setStartDateSuggestion(ZonedDateTime.now().plusHours(1));
        req.setTaskDuration(Duration.ofHours(2));

        Order order = new Order();
        order.setSuggestedPrice(BigDecimal.valueOf(900));
        order.setStatus(OrderStatus.WAITING_FOR_SPECIALIST_OFFER);

        Specialist specialist = new Specialist();
        specialist.setStatus(AccountStatus.APPROVED);

        when(orderService.findById(1L)).thenReturn(order);
        when(securityUtil.getCurrentUsername()).thenReturn("spec@example.com");
        when(specialistService.findByEmail("spec@example.com"))
                .thenReturn(specialist);
        when(orderService.save(any(Order.class))).thenReturn(order);
        when(offerRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));
        when(offerMapper.entityMapToResponse(any()))
                .thenReturn(new OfferResponse());

        OfferResponse response = offerService.submitOfferToOrder(req);

        assertNotNull(response);
        verify(orderService).save(order);
        verify(offerRepository).save(any(Offer.class));
    }

    @Test
    void testSubmitOfferToOrder_SpecialistNotApproved() {
        OfferSaveRequest req = new OfferSaveRequest();
        req.setOrderId(1L);
        req.setSuggestedPrice(BigDecimal.valueOf(1000));

        Order order = new Order();
        order.setSuggestedPrice(BigDecimal.valueOf(900));
        order.setStatus(OrderStatus.WAITING_FOR_SPECIALIST_COMING);

        Specialist specialist = new Specialist();
        specialist.setStatus(AccountStatus.INACTIVE);

        when(orderService.findById(1L)).thenReturn(order);
        when(securityUtil.getCurrentUsername())
                .thenReturn("spec@example.com");
        when(specialistService.findByEmail("spec@example.com"))
                .thenReturn(specialist);

        assertThrows(NotApprovedException.class,
                () -> offerService.submitOfferToOrder(req));
    }

    @Test
    void testSubmitOfferToOrder_InvalidPrice() {
        OfferSaveRequest req = new OfferSaveRequest();
        req.setOrderId(1L);
        req.setSuggestedPrice(BigDecimal.valueOf(800));

        Order order = new Order();
        order.setSuggestedPrice(BigDecimal.valueOf(900));
        order.setStatus(OrderStatus.WAITING_FOR_SPECIALIST_COMING);

        Specialist specialist = new Specialist();
        specialist.setStatus(AccountStatus.APPROVED);

        when(orderService.findById(1L)).thenReturn(order);
        when(securityUtil.getCurrentUsername())
                .thenReturn("spec@example.com");
        when(specialistService.findByEmail("spec@example.com"))
                .thenReturn(specialist);

        assertThrows(NotValidPriceException.class,
                () -> offerService.submitOfferToOrder(req));
    }

    @Test
    void testChooseOfferOfSpecialist_Success() {
        when(offerRepository.findById(foundOffer.getId()))
                .thenReturn(Optional.of(foundOffer));

        when(orderService.findById(foundOrder.getId())).thenReturn(foundOrder);

        when(offerRepository.save(any(Offer.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        Offer otherOffer = new Offer();
        otherOffer.setId(11L);
        otherOffer.setStatus(OfferStatus.PENDING);
        otherOffer.setOrderInformation(foundOrder);

        List<Offer> allOffers = new ArrayList<>();
        allOffers.add(foundOffer);
        allOffers.add(otherOffer);

        when(offerRepository.findAllByOrderInformation_Id(
                foundOrder.getId())).thenReturn(allOffers);

        when(offerRepository.saveAll(anyList())).thenReturn(allOffers);

        when(orderService.save(any(Order.class))).thenReturn(foundOrder);

        when(offerMapper.entityMapToResponse(any(Offer.class)))
                .thenReturn(expectedResponse);

        OfferResponse actualResponse = offerService
                .chooseOfferOfSpecialist(foundOffer.getId());

        assertEquals(OrderStatus.WAITING_FOR_SPECIALIST_COMING,
                foundOrder.getStatus());

        assertEquals(OfferStatus.ACCEPTED, foundOffer.getStatus());
        assertEquals(OfferStatus.REJECTED, otherOffer.getStatus());

        assertEquals(expectedResponse, actualResponse);

        verify(offerRepository).findById(foundOffer.getId());
        verify(orderService).findById(foundOrder.getId());
        verify(offerRepository).save(foundOffer);
        verify(offerRepository).findAllByOrderInformation_Id(foundOrder.getId());
        verify(offerRepository).saveAll(allOffers);
        verify(orderService).save(foundOrder);
        verify(offerMapper).entityMapToResponse(foundOffer);
    }


    @Test
    void testChooseOfferOfSpecialist_OrderStatusNotWaiting() {
        foundOrder.setStatus(OrderStatus.WAITING_FOR_SPECIALIST_COMING);

        when(offerRepository.findById(foundOffer.getId()))
                .thenReturn(Optional.of(foundOffer));
        when(orderService.findById(foundOrder.getId())).thenReturn(foundOrder);

        NotApprovedException exception = assertThrows(
                NotApprovedException.class, () -> {
            offerService.chooseOfferOfSpecialist(foundOffer.getId());
        });

        assertEquals("Order is no longer waiting for special offer",
                exception.getMessage());

        verify(offerRepository).findById(foundOffer.getId());
        verify(orderService).findById(foundOrder.getId());
        verifyNoMoreInteractions(offerRepository);
        verifyNoMoreInteractions(orderService);
    }

    @Test
    void testChooseOfferOfSpecialist_OfferNotFound() {
        when(offerRepository.findById(foundOffer.getId()))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            offerService.chooseOfferOfSpecialist(foundOffer.getId());
        });

        assertEquals("Offer of specialist not found",
                exception.getMessage());

        verify(offerRepository).findById(foundOffer.getId());
        verifyNoMoreInteractions(offerRepository);
        verifyNoInteractions(orderService);
    }

    @Test
    void testStartService_Success() {
        Offer offer = new Offer();
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.WAITING_FOR_SPECIALIST_COMING);

        offer.setId(1L);
        offer.setOrderInformation(order);
        offer.setStartDateSuggestion(ZonedDateTime.now().minusMinutes(1));
        offer.setStatus(OfferStatus.PENDING);

        OfferResponse expectedResponse = new OfferResponse();

        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(orderService.findById(order.getId())).thenReturn(order);
        when(orderService.save(order)).thenReturn(order);
        when(offerRepository.save(offer)).thenReturn(offer);
        when(offerMapper.entityMapToResponse(offer)).thenReturn(expectedResponse);

        OfferResponse response = offerService.startService(1L);

        assertNotNull(response);
        assertEquals(OfferStatus.ACCEPTED, offer.getStatus());
        assertEquals(OrderStatus.HAS_BEGIN, order.getStatus());
        assertEquals(expectedResponse, response);

        verify(offerRepository).findById(1L);
        verify(orderService).findById(order.getId());
        verify(orderService).save(order);
        verify(offerRepository).save(offer);
        verify(offerMapper).entityMapToResponse(offer);
    }

    @Test
    void testStartService_InvalidStatus() {
        Offer offer = new Offer();
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.DONE);
        offer.setOrderInformation(order);
        offer.setStartDateSuggestion(ZonedDateTime.now().minusMinutes(1));

        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(orderService.findById(order.getId())).thenReturn(order);

        NotApprovedException exception = assertThrows(NotApprovedException.class,
                () -> offerService.startService(1L));

        assertEquals("This offer is not approved", exception.getMessage());

        verify(offerRepository).findById(1L);
        verify(orderService).findById(order.getId());
        verifyNoMoreInteractions(offerRepository, orderService);
    }

    @Test
    void testStartService_BeforeSuggestedTime() {
        Offer offer = new Offer();
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.WAITING_FOR_SPECIALIST_COMING);
        offer.setOrderInformation(order);
        offer.setStartDateSuggestion(ZonedDateTime.now().plusHours(1));

        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(orderService.findById(order.getId())).thenReturn(order);

        NotApprovedException exception = assertThrows(NotApprovedException.class,
                () -> offerService.startService(1L));

        assertEquals("Start time is before the suggested time with specialist",
                exception.getMessage());

        verify(offerRepository).findById(1L);
        verify(orderService).findById(order.getId());
        verifyNoMoreInteractions(offerRepository, orderService);
    }

    @Test
    void testEndService_Success() {
        Offer offer = new Offer();
        Order order = new Order();
        order.setStatus(OrderStatus.HAS_BEGIN);
        offer.setOrderInformation(order);
        offer.setStatus(OfferStatus.ACCEPTED);

        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(orderService.save(order)).thenReturn(order);
        when(offerRepository.save(any())).thenAnswer(
                i -> i.getArgument(0));
        when(offerMapper.entityMapToResponse(any()))
                .thenReturn(new OfferResponse());

        OfferResponse response = offerService.endService(1L);

        assertNotNull(response);
        assertEquals(OfferStatus.DONE, offer.getStatus());
        assertEquals(OrderStatus.DONE, order.getStatus());
    }

    @Test
    void testEndService_InvalidStatus() {
        Offer offer = new Offer();
        Order order = new Order();
        order.setStatus(OrderStatus.WAITING_FOR_SPECIALIST_COMING);
        offer.setOrderInformation(order);

        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

        assertThrows(NotApprovedException.class,
                () -> offerService.endService(1L));
    }

    @Test
    void testFindByOfferOfSpecialistId() {
        Specialist specialist = new Specialist();
        specialist.setId(1L);
        when(securityUtil.getCurrentUsername()).thenReturn("spec@example.com");
        when(specialistService.findByEmail("spec@example.com"))
                .thenReturn(specialist);

        Offer offer = new Offer();
        List<Offer> offerList = Collections.singletonList(offer);
        Page<Offer> page = new PageImpl<>(offerList);
        when(offerRepository.findAllBySpecialistId(1L,
                PageRequest.of(0,10))).thenReturn(page);
        when(offerMapper.entityMapToResponse(any())).thenReturn(new OfferResponse());

        Page<OfferResponse> responses = offerService.findByOfferOfSpecialistId(
                PageRequest.of(0,10));

        assertNotNull(responses);
        assertEquals(1, responses.getTotalElements());
    }

    @Test
    void testFindOrdersBySpecialistId() {
        Specialist specialist = new Specialist();
        specialist.setId(1L);
        when(securityUtil.getCurrentUsername()).thenReturn("spec@example.com");
        when(specialistService.findByEmail("spec@example.com"))
                .thenReturn(specialist);

        when(offerRepository.findOrdersBySpecialistId(
                1L, PageRequest.of(0, 10)))
                .thenReturn(Page.empty());

        Page<OrderResponse> page = offerService.findOrdersBySpecialistId(
                PageRequest.of(0, 10));
        assertNotNull(page);
    }

    @Test
    void testFindAllOffersBySuggestedPrice() {
        Order order = new Order();
        when(orderService.findById(1L)).thenReturn(order);

        Offer offer = new Offer();
        Page<Offer> offers = new PageImpl<>(Collections.singletonList(offer));
        when(offerRepository.findAllByOrderInformation_Id(
                1L, PageRequest.of(0, 10)))
                .thenReturn(offers);
        when(offerMapper.entityMapToResponse(any()))
                .thenReturn(new OfferResponse());

        Page<OfferResponse> result = offerService.findAllOffersBySuggestedPrice(
                1L, PageRequest.of(0, 10));
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testFindAllOffersBySpecialistScore() {
        Order order = new Order();
        when(orderService.findById(1L)).thenReturn(order);

        Offer offer = new Offer();
        Page<Offer> offers = new PageImpl<>(Collections.singletonList(offer));
        when(offerRepository.findAllByOrderInformation_Id(1L,
                PageRequest.of(0, 10)))
                .thenReturn(offers);
        when(offerMapper.entityMapToResponse(any()))
                .thenReturn(new OfferResponse());

        Page<OfferResponse> result = offerService.findAllOffersBySpecialistScore(
                1L, PageRequest.of(0, 10));
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testPaySpecialist_Success() {
        Order order = new Order();
        order.setId(100L);
        Customer customer = new Customer();
        Wallet customerWallet = new Wallet();
        customerWallet.setBalance(BigDecimal.valueOf(200));
        customer.setWallet(customerWallet);
        order.setCustomer(customer);
        order.setStatus(OrderStatus.DONE);

        Specialist specialist = new Specialist();
        Wallet specialistWallet = new Wallet();
        specialistWallet.setBalance(BigDecimal.valueOf(50));
        specialist.setWallet(specialistWallet);
        specialist.setScore(5.0);
        specialist.setStatus(AccountStatus.APPROVED);
        specialist.setId(1L);

        Offer offer = new Offer();
        offer.setId(1L);
        offer.setStatus(OfferStatus.DONE);
        offer.setSuggestedPrice(BigDecimal.valueOf(100));
        ZonedDateTime now = ZonedDateTime.now();
        offer.setStartDateSuggestion(now.minusHours(3));
        offer.setTaskDuration(Duration.ofHours(1));
        offer.setOrderInformation(order);
        offer.setSpecialist(specialist);

        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(orderService.findById(100L)).thenReturn(order);
        when(specialistService.findById(1L)).thenReturn(specialist);

        when(specialistService.save(any())).thenAnswer(i -> {
            Specialist sp = i.getArgument(0);
            specialist.setScore(sp.getScore());
            specialist.setStatus(sp.getStatus());
            return sp;
        });

        when(walletService.save(any())).thenAnswer(
                i -> i.getArgument(0));
        when(transactionService.save(any())).thenAnswer(
                i -> i.getArgument(0));
        when(offerRepository.save(any())).thenAnswer(
                i -> i.getArgument(0));
        when(offerMapper.entityMapToPaymentResponse(any()))
                .thenReturn(new PaymentResponse());

        PaymentResponse response = offerService.paySpecialist(1L);

        assertNotNull(response);
        assertEquals(OfferStatus.PAID, offer.getStatus());
        assertTrue(specialist.getScore() < 5.0,
                "Score should be decreased due to penalty");
    }



    @Test
    void testPaySpecialist_AlreadyPaid() {
        Offer offer = new Offer();
        offer.setStatus(OfferStatus.PAID);

        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

        assertThrows(NotApprovedException.class,
                () -> offerService.paySpecialist(1L));
    }

    @Test
    void testPaySpecialist_NotDoneStatus() {
        Offer offer = new Offer();
        offer.setStatus(OfferStatus.PENDING);

        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

        assertThrows(NotApprovedException.class,
                () -> offerService.paySpecialist(1L));
    }

    @Test
    void testPaySpecialist_InsufficientBalance() {
        Offer offer = new Offer();
        offer.setStatus(OfferStatus.DONE);
        offer.setSuggestedPrice(BigDecimal.valueOf(100));
        ZonedDateTime now = ZonedDateTime.now();
        offer.setStartDateSuggestion(now.minusHours(3));
        offer.setTaskDuration(Duration.ofHours(1));

        Specialist specialist = new Specialist();
        specialist.setId(123L);
        offer.setSpecialist(specialist);

        Order order = new Order();
        order.setId(200L);

        Customer customer = new Customer();
        Wallet customerWallet = new Wallet();
        customerWallet.setBalance(BigDecimal.valueOf(50));
        customer.setWallet(customerWallet);
        order.setCustomer(customer);
        order.setStatus(OrderStatus.DONE);

        offer.setOrderInformation(order);

        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(orderService.findById(200L)).thenReturn(order);
        when(specialistService.findById(123L)).thenReturn(specialist);

        assertThrows(NotValidPriceException.class,
                () -> offerService.paySpecialist(1L));
    }

}
