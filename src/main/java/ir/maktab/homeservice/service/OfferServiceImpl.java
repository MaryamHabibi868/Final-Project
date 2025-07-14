package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.*;
import ir.maktab.homeservice.domains.enumClasses.AccountStatus;
import ir.maktab.homeservice.domains.enumClasses.OfferStatus;
import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import ir.maktab.homeservice.domains.enumClasses.TransactionType;
import ir.maktab.homeservice.dto.OfferSaveRequest;
import ir.maktab.homeservice.dto.OfferResponse;
import ir.maktab.homeservice.dto.OrderResponse;
import ir.maktab.homeservice.exception.NotApprovedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.exception.NotValidPriceException;
import ir.maktab.homeservice.mapper.OfferMapper;
import ir.maktab.homeservice.mapper.OrderMapper;
import ir.maktab.homeservice.repository.OfferRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class OfferServiceImpl
        extends BaseServiceImpl<Offer, Long, OfferRepository>
        implements OfferService {

    private final OfferMapper offerMapper;
    private final OrderService orderService;
    private final SpecialistService specialistService;
    private final TransactionService transactionService;
    private final WalletService walletService;
    private final OrderMapper orderMapper;

    public OfferServiceImpl(OfferRepository repository,
                            OfferMapper offerMapper,
                            OrderService orderService,
                            SpecialistService specialistService,
                            TransactionService transactionService,
                            WalletService walletService,
                            OrderMapper orderMapper) {
        super(repository);
        this.offerMapper = offerMapper;
        this.orderService = orderService;
        this.specialistService = specialistService;
        this.transactionService = transactionService;
        this.walletService = walletService;
        this.orderMapper = orderMapper;
    }

    // ☑️ final check
    //✅ ok
    @Transactional
    @Override
    public OfferResponse submitOfferToOrder(
            OfferSaveRequest request) {
        Order foundOrder = orderService.
                findById(request.getOrderId());

        Specialist foundSpecialist = specialistService.
                findById(request.getSpecialistId());

        if (foundSpecialist.getStatus() != AccountStatus.APPROVED) {
            throw new NotApprovedException("Specialist Not Approved");
        }
        if (request.getSuggestedPrice()
                .compareTo(foundOrder.getSuggestedPrice()) < 0) {
            throw new NotValidPriceException(
                    "Suggested price is less than the price of this Order");
        }

        OrderStatus status = foundOrder.getStatus();
        if (status == OrderStatus.DONE || status == OrderStatus.HAS_BEGIN
                || status == OrderStatus.WAITING_FOR_SPECIALIST_COMING) {
            throw new NotApprovedException("Order is not waiting for special offer");
        }

        foundOrder.setStatus(OrderStatus.WAITING_FOR_CHOOSING_SPECIALIST);
        orderService.save(foundOrder);

        Offer offer = new Offer();
        offer.setSuggestedPrice(request.getSuggestedPrice());
        offer.setStartDateSuggestion(request.getStartDateSuggestion());
        offer.setTaskDuration(request.getTaskDuration());
        offer.setStatus(OfferStatus.PENDING);
        offer.setSpecialist(foundSpecialist);
        offer.setOrderInformation(foundOrder);
        Offer save = repository.save(offer);
        return offerMapper.entityMapToResponse(save);
    }

   /* @Override
    public List<OfferOfSpecialistRequest>
    findAllOffersOfSpecialistsByCustomerId(
            CustomerUpdateRequest request) {
        Customer customer = customerService.findByIdIsActiveTrue(request.getId());
        return null;
*//*
                repository.findAllByCustomerIdOrderBySuggestedPriceAsc(customer.getId());
*//*
    }*/

    // ☑️ final check
    //✅ ok
    @Override
    public OfferResponse chooseOfferOfSpecialist(
            Long offerId) {
        Offer foundOffer = repository.findById(
                offerId).orElseThrow(
                () -> new NotFoundException("Offer of specialist not found")
        );

        Order foundOrder = orderService.findById(foundOffer.getOrderInformation().getId());
        if (foundOrder.getStatus()
                != OrderStatus.WAITING_FOR_CHOOSING_SPECIALIST) {
            throw new NotApprovedException("Order is no longer waiting for special offer");
        }

        foundOrder.setStatus(OrderStatus.WAITING_FOR_SPECIALIST_COMING);
        orderService.save(foundOrder);

        foundOffer.setSpecialist(Specialist.builder()
                .id(foundOffer.getSpecialist().getId()).build());
        foundOffer.setStatus(OfferStatus.ACCEPTED);
        Offer save = repository.save(foundOffer);
        List<Offer> allByOrderId = repository.findAllByOrderInformation_Id(foundOffer.getOrderInformation().getId());
        allByOrderId.forEach(offer -> offer.setStatus(OfferStatus.REJECTED));
        repository.saveAll(allByOrderId);
        return offerMapper.entityMapToResponse(save);
    }

    // ☑️ final check
    //✅ ok
    @Override
    public OfferResponse startService(Long offerId) {
        Offer foundOffer = repository.findById(offerId)
                .orElseThrow(
                        () -> new NotFoundException("Offer not found")
                );

        Order foundOrder = orderService.findById(foundOffer.getOrderInformation().getId());

        if (foundOrder.
                getStatus() != OrderStatus.WAITING_FOR_SPECIALIST_COMING) {
            throw new NotApprovedException("This offer is not approved");
        }

        if (ZonedDateTime.now().isBefore(foundOffer.getStartDateSuggestion())) {
            throw new NotApprovedException
                    ("Start time is before the suggested time with specialist");
        }

        foundOrder.setStatus(OrderStatus.HAS_BEGIN);
        orderService.save(foundOrder);
        foundOffer.setStatus(OfferStatus.ACCEPTED);
        Offer save = repository.save(foundOffer);
        return offerMapper.entityMapToResponse(save);
    }

    // ☑️ final check
    //✅
    @Override
    public OfferResponse endService(Long offerId) {
        Offer foundOffer = repository.findById(offerId)
                .orElseThrow(
                        () -> new NotFoundException("Offer of specialist not found")
                );

        if (foundOffer.getOrderInformation().getStatus() !=
                OrderStatus.HAS_BEGIN) {
            throw new NotApprovedException("This offer is not begin");
        }
        foundOffer.getOrderInformation().setStatus(OrderStatus.DONE);
        foundOffer.setStatus(OfferStatus.DONE);
        Offer save = repository.save(foundOffer);
        orderService.save(foundOffer.getOrderInformation());
        return offerMapper.entityMapToResponse(save);
    }

    //✅
    @Override
    public List<OfferResponse> findByOfferOfSpecialistId(
            Long specialistId) {
        Specialist specialist = specialistService.findById(specialistId);

        return repository.findAllBySpecialistId(specialistId)
                .stream()
                .map(offerMapper::entityMapToResponse)
                .toList();
    }

    // ☑️ final check
    @Override
    public List<OrderResponse> findOrdersBySpecialistId(Long specialistId) {
      return repository.findOrdersBySpecialistId(specialistId)
                .stream().map(orderMapper :: entityMapToResponse)
                .toList();
    }


    // ☑️ final check
    @Override
    public List<OfferResponse> findAllOffersBySuggestedPrice(Long orderId) {
        orderService.findById(orderId);

        Sort sort = Sort.by(Sort.Direction.ASC, "suggestedPrice");

        List<Offer> offers = repository.findAllByOrderInformation_Id(orderId, sort);

        return offers.stream()
                .map(offerMapper::entityMapToResponse)
                .toList();
    }

    // ☑️ final check
    @Override
    public List<OfferResponse> findAllOffersBySpecialistScore(Long orderId) {
        orderService.findById(orderId);

        Sort sort = Sort.by(Sort.Direction.DESC, "specialist.score");

        List<Offer> offers = repository.findAllByOrderInformation_Id(orderId, sort);

        return offers.stream()
                .map(offerMapper::entityMapToResponse)
                .toList();
    }


    /*//✅
    @Override
    public List<OfferResponse>
    findAllOfferOrderByCustomerId(Long customerId) {
        return repository.findAllByOrder_Id(customerId)
                .stream()
                .map(offerMapper::entityMapToResponse)
                .toList();
    }*/

    // ☑️ final check
    //✅
    @Transactional
    @Override
    public void paySpecialist(Long offerId) {
        ZonedDateTime actualEndService = ZonedDateTime.now();

        Offer foundOffer = repository.findById(offerId).orElseThrow(
                () -> new NotFoundException("Offer not found")
        );

        if (foundOffer.getStatus() == OfferStatus.PAID) {
            throw new NotApprovedException("This offer is paid");
        }

        if (!(foundOffer.getStatus() == OfferStatus.DONE)) {
            throw new NotApprovedException("This offer in Not done");
        }

        Order foundOrder = orderService.findById(
                foundOffer.getOrderInformation().getId());

        ZonedDateTime timeToComplete = foundOffer.getStartDateSuggestion()
                .plus(foundOffer.getTaskDuration());

        Specialist foundSpecialist = specialistService.findById(
                foundOffer.getSpecialist().getId());

        Customer foundCustomer = foundOrder.getCustomer();

        Long customerId = foundCustomer.getId();

        Wallet customerWallet = foundCustomer.getWallet();

        Wallet specialistWallet = foundSpecialist.getWallet();

        if (customerWallet.getBalance()
                .compareTo(foundOffer.getSuggestedPrice()) < 0) {
            throw new NotValidPriceException("Balance in customer wallet is lower than suggested price");
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(foundOffer.getSuggestedPrice());
        transaction.setDate(ZonedDateTime.now());
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setWallet(customerWallet);
        customerWallet.setBalance(
                customerWallet.getBalance().subtract(foundOffer.getSuggestedPrice()));
        walletService.save(customerWallet);
        transactionService.save(transaction);

        Transaction transaction1 = new Transaction();
        transaction1.setAmount(foundOffer.getSuggestedPrice()
                .multiply(BigDecimal.valueOf(0.70)));

        transaction1.setDate(ZonedDateTime.now());
        transaction1.setType(TransactionType.DEPOSIT);
        transaction1.setWallet(specialistWallet);
        specialistWallet.setBalance(specialistWallet.getBalance().add(
                foundOffer.getSuggestedPrice()
                        .multiply(BigDecimal.valueOf(0.70))));
        walletService.save(specialistWallet);
        transactionService.save(transaction1);

        foundOffer.setStatus(OfferStatus.PAID);
        repository.save(foundOffer);

        if (actualEndService.isAfter(timeToComplete)) {
            long hoursLate = Duration.between(timeToComplete, actualEndService).toHours();
            int penalty = (int) hoursLate * -1;
            Double score = foundSpecialist.getScore();
            score += penalty;
        }

    }

}
