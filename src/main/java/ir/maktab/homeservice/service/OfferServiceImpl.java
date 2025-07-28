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
import ir.maktab.homeservice.security.SecurityUtil;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

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
    private final SecurityUtil securityUtil;

    public OfferServiceImpl(OfferRepository repository,
                            OfferMapper offerMapper,
                            OrderService orderService,
                            SpecialistService specialistService,
                            TransactionService transactionService,
                            WalletService walletService,
                            OrderMapper orderMapper,
                            SecurityUtil securityUtil) {
        super(repository);
        this.offerMapper = offerMapper;
        this.orderService = orderService;
        this.specialistService = specialistService;
        this.transactionService = transactionService;
        this.walletService = walletService;
        this.orderMapper = orderMapper;
        this.securityUtil = securityUtil;
    }


    @Transactional
    @Override
    public OfferResponse submitOfferToOrder(
            OfferSaveRequest request) {
        Order foundOrder = orderService.
                findById(request.getOrderId());

        String email = securityUtil.getCurrentUsername();

        Specialist foundSpecialist = specialistService.findByEmail(email);

        /*Specialist foundSpecialist = specialistService.
                findById(request.getSpecialistId());*/

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


    @Override
    public Page<OfferResponse> findByOfferOfSpecialistId(
            /*Long specialistId,*/ Pageable pageable) {

        String email = securityUtil.getCurrentUsername();
        Specialist specialist = specialistService.findByEmail(email);

        Long specialistId = specialist.getId();

        /*Specialist specialist = specialistService.findById(specialistId);*/

        return repository.findAllBySpecialistId(specialistId, pageable)
                .map(offerMapper::entityMapToResponse);
    }


    @Override
    public Page<OrderResponse> findOrdersBySpecialistId(
            /*Long specialistId,*/ Pageable pageable) {

        String email = securityUtil.getCurrentUsername();
        Specialist foundSpecialist = specialistService.findByEmail(email);

        /*Specialist foundSpecialist = specialistService.findById(specialistId);*/

        Long specialistId = foundSpecialist.getId();

        Set<Offer> offers = foundSpecialist.getOffers();

        return repository.findOrdersBySpecialistId(specialistId, pageable);

        /*List<Long> allowedOrderIds = offers.stream()
                .filter(offer -> offer.getStatus() == OfferStatus.DONE
                        || offer.getStatus() == OfferStatus.PAID
                        || offer.getStatus() == OfferStatus.ACCEPTED)
                .map(offer -> offer.getOrderInformation().getId())
                .distinct()
                .toList();

        return repository.findOrdersBySpecialistId(specialistId, pageable)
                .map(order -> {
                    if (allowedOrderIds.contains(order.getId())) {
                        return orderMapper.entityMapToResponse(order);
                    } else {
                        return orderMapper.entityMapToResponseByFilter(order);
                    }
                });*/
    }


    @Override
    public Page<OfferResponse> findAllOffersBySuggestedPrice(
            Long orderId, Pageable pageable) {

        orderService.findById(orderId);


        Page<Offer> offers = repository.findAllByOrderInformation_Id(orderId, pageable);

        return offers.map(offerMapper::entityMapToResponse);
    }


    @Override
    public Page<OfferResponse> findAllOffersBySpecialistScore(
            Long orderId, Pageable pageable) {
        orderService.findById(orderId);


        Page<Offer> offers = repository.findAllByOrderInformation_Id(orderId, pageable);

        return offers.map(offerMapper::entityMapToResponse);
    }


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
            double newScore = score + penalty;
            foundSpecialist.setScore(newScore);
            if (newScore < 0.0) {
                foundSpecialist.setStatus(AccountStatus.INACTIVE);
            }
            specialistService.save(foundSpecialist);
        }
    }
}
