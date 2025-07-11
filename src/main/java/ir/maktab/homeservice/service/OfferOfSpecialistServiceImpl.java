package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.*;
import ir.maktab.homeservice.domains.enumClasses.AccountStatus;
import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import ir.maktab.homeservice.domains.enumClasses.TransactionType;
import ir.maktab.homeservice.dto.OfferOfSpecialistRequest;
import ir.maktab.homeservice.dto.OfferOfSpecialistResponse;
import ir.maktab.homeservice.exception.NotApprovedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.exception.NotValidPriceException;
import ir.maktab.homeservice.mapper.OfferOfSpecialistMapper;
import ir.maktab.homeservice.repository.OfferOfSpecialistRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class OfferOfSpecialistServiceImpl
        extends BaseServiceImpl<OfferOfSpecialist, Long, OfferOfSpecialistRepository>
        implements OfferOfSpecialistService {

    private final OfferOfSpecialistMapper offerOfSpecialistMapper;
    private final OrderOfCustomerService orderOfCustomerService;
    private final SpecialistService specialistService;
    private final WalletService walletService;

    public OfferOfSpecialistServiceImpl(OfferOfSpecialistRepository repository,
                                        OfferOfSpecialistMapper offerOfSpecialistMapper,
                                        OrderOfCustomerService orderOfCustomerService,
                                        SpecialistService specialistService,
                                        WalletService walletService) {
        super(repository);
        this.offerOfSpecialistMapper = offerOfSpecialistMapper;
        this.orderOfCustomerService = orderOfCustomerService;
        this.specialistService = specialistService;
        this.walletService = walletService;
    }

    //✅ ok
    @Override
    public OfferOfSpecialistResponse submitOfferToOrder(
            OfferOfSpecialistRequest request) {
        OrderOfCustomer foundOrderOfCustomer = orderOfCustomerService.
                findById(request.getOrderOfCustomerId());

        Specialist foundSpecialist = specialistService.
                findById(request.getSpecialistId());

        if (foundSpecialist.getAccountStatus() != AccountStatus.APPROVED) {
            throw new NotApprovedException("Specialist Not Approved");
        }
        if (request.getSuggestedPrice()
                .compareTo(foundOrderOfCustomer.getSuggestedPrice()) < 0) {
            throw new NotValidPriceException(
                    "Suggested price is less than the price of this Order");
        }
        if (foundOrderOfCustomer.getOrderStatus() !=
                OrderStatus.WAITING_FOR_SPECIALIST_OFFER) {
            throw new NotApprovedException("Order is not waiting for special offer");
        }

        foundOrderOfCustomer.setOrderStatus(OrderStatus.WAITING_FOR_CHOOSING_SPECIALIST);
        orderOfCustomerService.save(foundOrderOfCustomer);

        OfferOfSpecialist offerOfSpecialist = new OfferOfSpecialist();
        offerOfSpecialist.setSuggestedPrice(request.getSuggestedPrice());
        offerOfSpecialist.setStartDateSuggestion(request.getStartDateSuggestion());
        offerOfSpecialist.setTaskDuration(request.getTaskDuration());
        offerOfSpecialist.setSpecialist(foundSpecialist);
        offerOfSpecialist.setOrderOfCustomer(foundOrderOfCustomer);
        OfferOfSpecialist save = repository.save(offerOfSpecialist);
        return offerOfSpecialistMapper.entityMapToResponse(save);
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

    //✅ ok
    @Override
    public OfferOfSpecialistResponse chooseOfferOfSpecialist(
            OfferOfSpecialistResponse request) {
        OfferOfSpecialist foundOfferOfSpecialist = repository.findById(
                request.getId()).orElseThrow(
                () -> new NotFoundException("Offer of specialist not found")
        );
        if (foundOfferOfSpecialist.getOrderOfCustomer().getOrderStatus()
                != OrderStatus.WAITING_FOR_SPECIALIST_OFFER) {
            throw new NotApprovedException("Order is not waiting for special offer");
        }

        foundOfferOfSpecialist.getOrderOfCustomer().
                setOrderStatus(OrderStatus.WAITING_FOR_SPECIALIST_COMING);
        foundOfferOfSpecialist.setSpecialist(Specialist.builder()
                .id(request.getSpecialistId()).build());
        OfferOfSpecialist save = repository.save(foundOfferOfSpecialist);
        return offerOfSpecialistMapper.entityMapToResponse(save);
    }

    //✅ ok
    @Override
    public OfferOfSpecialistResponse startService(OfferOfSpecialistResponse request) {
        OfferOfSpecialist foundOfferOfSpecialist = repository.findById(request.getId())
                .orElseThrow(
                        () -> new NotFoundException("Offer of specialist not found")
                );

        if (foundOfferOfSpecialist.getOrderOfCustomer().
                getOrderStatus() != OrderStatus.WAITING_FOR_SPECIALIST_COMING) {
            throw new NotApprovedException("This offer is not approved");
        }

        if (foundOfferOfSpecialist.getStartDateSuggestion().isBefore(ZonedDateTime.now())) {
            throw new NotApprovedException
                    ("Start time is before the suggested time with specialist");
        }

        foundOfferOfSpecialist.getOrderOfCustomer().setOrderStatus(
                OrderStatus.HAS_BEGIN);
        OfferOfSpecialist save = repository.save(foundOfferOfSpecialist);
        return offerOfSpecialistMapper.entityMapToResponse(save);
    }

    //✅
    @Override
    public OfferOfSpecialistResponse endService(OfferOfSpecialistResponse request) {
        OfferOfSpecialist foundOfferOfSpecialist = repository.findById(request.getId())
                .orElseThrow(
                        () -> new NotFoundException("Offer of specialist not found")
                );

        if (foundOfferOfSpecialist.getOrderOfCustomer().getOrderStatus() !=
                OrderStatus.HAS_BEGIN) {
            throw new NotApprovedException("This offer is not begin");
        }
        foundOfferOfSpecialist.getOrderOfCustomer().setOrderStatus(OrderStatus.DONE);
        OfferOfSpecialist save = repository.save(foundOfferOfSpecialist);
        return offerOfSpecialistMapper.entityMapToResponse(save);
    }

    //✅
    @Override
    public List<OfferOfSpecialistResponse> findByOfferOfSpecialistId(
            Long specialistId) {
        Specialist specialist = specialistService.findById(specialistId);

        return repository.findAllBySpecialistId(specialistId)
                .stream()
                .map(offerOfSpecialistMapper::entityMapToResponse)
                .toList();
    }

    //✅
    @Override
    public List<OfferOfSpecialistResponse>
    findAllOfferOfSpecialistOrderByCustomerId(Long customerId) {
        return repository.findAllByOrderOfCustomer_CustomerId(customerId)
                .stream()
                .map(offerOfSpecialistMapper::entityMapToResponse)
                .toList();
    }

    public void paySpecialist(OfferOfSpecialistResponse response,
                              ZonedDateTime actualEndService) {

        OrderOfCustomer foundOrder = orderOfCustomerService.findById(
                response.getOrderOfCustomerId());

        ZonedDateTime timeToComplete = response.getStartDateSuggestion()
                .plus(response.getTaskDuration());

        Specialist foundSpecialist = specialistService.findById(
                response.getSpecialistId());

        Customer foundCustomer = foundOrder.getCustomer();
        Long customerId = foundCustomer.getId();

        Wallet customerWallet = foundCustomer.getWallet();

        Wallet specialistWallet = foundSpecialist.getWallet();

        while (foundOrder.getOrderStatus() == OrderStatus.DONE) {

            if (customerWallet.getBalance()
                    .compareTo(response.getSuggestedPrice()) < 0) {

                Transaction transaction = new Transaction();
                transaction.setAmount(response.getSuggestedPrice());
                transaction.setTransactionDate(ZonedDateTime.now());
                transaction.setTransactionType(TransactionType.DEPOSIT);
                customerWallet.getBalance().add(response.getSuggestedPrice());

                Transaction transaction1 = new Transaction();
                transaction1.setAmount(response.getSuggestedPrice());
                transaction1.setTransactionDate(ZonedDateTime.now());
                transaction1.setTransactionType(TransactionType.WITHDRAWAL);
                customerWallet.getBalance().subtract(response.getSuggestedPrice());

                Transaction transaction2 = new Transaction();
                transaction2.setAmount(response.getSuggestedPrice()
                        .multiply(BigDecimal.valueOf(0.70)));

                transaction2.setTransactionDate(ZonedDateTime.now());
                transaction2.setTransactionType(TransactionType.DEPOSIT);
                specialistWallet.getBalance().add(
                        response.getSuggestedPrice()
                                .multiply(BigDecimal.valueOf(0.70)));

                if (actualEndService.isAfter(timeToComplete)) {
                    long hoursLate = Duration.between(timeToComplete, actualEndService).toHours();
                    int penalty = (int) hoursLate * -1;
                    Integer score = foundSpecialist.getScore();
                    score += penalty;
                }
            } else {
                Transaction transaction1 = new Transaction();
                transaction1.setAmount(response.getSuggestedPrice());
                transaction1.setTransactionDate(ZonedDateTime.now());
                transaction1.setTransactionType(TransactionType.WITHDRAWAL);
                customerWallet.getBalance().subtract(response.getSuggestedPrice());

                Transaction transaction2 = new Transaction();
                transaction2.setAmount(response.getSuggestedPrice()
                        .multiply(BigDecimal.valueOf(0.70)));

                transaction2.setTransactionDate(ZonedDateTime.now());
                transaction2.setTransactionType(TransactionType.DEPOSIT);
                specialistWallet.getBalance().add(
                        response.getSuggestedPrice()
                                .multiply(BigDecimal.valueOf(0.70)));
            }
            throw new NotApprovedException("This offer in Not done");
        }
    }
}
