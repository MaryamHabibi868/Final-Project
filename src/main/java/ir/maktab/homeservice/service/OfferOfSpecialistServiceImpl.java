package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.OfferOfSpecialist;
import ir.maktab.homeservice.domains.OrderOfCustomer;
import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.domains.enumClasses.AccountStatus;
import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import ir.maktab.homeservice.dto.CustomerUpdateRequest;
import ir.maktab.homeservice.dto.OfferOfSpecialistRequest;
import ir.maktab.homeservice.dto.OfferOfSpecialistResponse;
import ir.maktab.homeservice.exception.NotApprovedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.exception.NotValidPriceException;
import ir.maktab.homeservice.mapper.OfferOfSpecialistMapper;
import ir.maktab.homeservice.repository.OfferOfSpecialistRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class OfferOfSpecialistServiceImpl
        extends BaseServiceImpl<OfferOfSpecialist, Long, OfferOfSpecialistRepository>
        implements OfferOfSpecialistService {

    private final OfferOfSpecialistMapper offerOfSpecialistMapper;
    private final CustomerService customerService;
    private final OrderOfCustomerService orderOfCustomerService;
    private final SpecialistService specialistService;

    public OfferOfSpecialistServiceImpl(OfferOfSpecialistRepository repository,
                                        OfferOfSpecialistMapper offerOfSpecialistMapper,
                                        CustomerService customerService,
                                        OrderOfCustomerService orderOfCustomerService,
                                        SpecialistService specialistService) {
        super(repository);
        this.offerOfSpecialistMapper = offerOfSpecialistMapper;
        this.customerService = customerService;
        this.orderOfCustomerService = orderOfCustomerService;
        this.specialistService = specialistService;
    }

    //✅ ok
    @Override
    public OfferOfSpecialistResponse submitOfferToOrder(
            OfferOfSpecialistRequest request,
            Long orderOfCustomerId) {
        OrderOfCustomer foundOrderOfCustomer = orderOfCustomerService.
                findById(orderOfCustomerId).orElseThrow(
                        () -> new NotFoundException("Order of customer Not Found"));

        Specialist foundSpecialist = specialistService.
                findById(request.getSpecialistId()).orElseThrow(
                        () -> new NotFoundException("Special ist Not Found"));

        if (!(foundSpecialist.getAccountStatus() == AccountStatus.APPROVED)) {
            throw new NotApprovedException("Special ist Not Approved");
        }
        if (request.getSuggestedPrice()
                .compareTo(foundOrderOfCustomer.getSuggestedPrice()) < 0) {
            throw new NotValidPriceException(
                    "Suggested price is less than the price of this Order");
        }
        if (!(foundOrderOfCustomer.getOrderStatus() ==
                OrderStatus.WAITING_FOR_SPECIALIST_OFFER)) {
            throw new NotApprovedException("Order is not waiting for special offer");
        }

        foundOrderOfCustomer.setOrderStatus(OrderStatus.WAITING_FOR_CHOOSING_SPECIALIST);
        orderOfCustomerService.save(foundOrderOfCustomer);

        OfferOfSpecialist offerOfSpecialist = new OfferOfSpecialist();
        offerOfSpecialist.setSuggestedPrice(request.getSuggestedPrice());
        offerOfSpecialist.setStartDateSuggestion(request.getStartDateSuggestion());
        offerOfSpecialist.setTaskDuration(request.getTaskDuration());
        OfferOfSpecialist save = repository.save(offerOfSpecialist);
        return offerOfSpecialistMapper.entityMapToResponse(save);
    }

    @Override
    public List<OfferOfSpecialistRequest>
    findAllOffersOfSpecialistsByCustomerId(
            CustomerUpdateRequest request) {
        Customer customer = customerService.findById(request.getId()).get();
        return null;
/*
                repository.findAllByCustomerIdOrderBySuggestedPriceAsc(customer.getId());
*/
    }

    @Override
    public OfferOfSpecialistRequest chooseOfferOfSpecialist(
            OfferOfSpecialistRequest request) {

        OfferOfSpecialist offerOfSpecialist =
                offerOfSpecialistMapper.offerOfSpecialistDTOMapToEntity(request);

        offerOfSpecialist.getOrderOfCustomer().
                setOrderStatus(OrderStatus.WAITING_FOR_SPECIALIST_COMING);
        OfferOfSpecialist save = repository.save(offerOfSpecialist);
        return offerOfSpecialistMapper.offerOfSpecialistMapToDTO(save);
    }

    @Override
    public OfferOfSpecialistRequest startService(OfferOfSpecialistRequest request) {
        OfferOfSpecialist offerOfSpecialist =
                offerOfSpecialistMapper.offerOfSpecialistDTOMapToEntity(request);

        if (offerOfSpecialist.getOrderOfCustomer().
                getOrderStatus() != OrderStatus.WAITING_FOR_SPECIALIST_COMING) {
            throw new NotApprovedException("This offer is not approved");
        }

        if (offerOfSpecialist.getStartDateSuggestion().isBefore(ZonedDateTime.now())) {
            throw new NotApprovedException
                    ("Start time is before the suggested time with specialist");
        }

        offerOfSpecialist.getOrderOfCustomer().setOrderStatus(OrderStatus.HAS_BEGIN);
        OfferOfSpecialist save = repository.save(offerOfSpecialist);
        return offerOfSpecialistMapper.offerOfSpecialistMapToDTO(save);
    }

    @Override
    public OfferOfSpecialistRequest endService(OfferOfSpecialistRequest request) {
        OfferOfSpecialist offerOfSpecialist = offerOfSpecialistMapper
                .offerOfSpecialistDTOMapToEntity(request);

        if (offerOfSpecialist.getOrderOfCustomer().getOrderStatus() !=
                OrderStatus.HAS_BEGIN) {
            throw new NotApprovedException("This offer is not begin");
        }
        offerOfSpecialist.getOrderOfCustomer().setOrderStatus(OrderStatus.DONE);
        OfferOfSpecialist save = repository.save(offerOfSpecialist);
        return offerOfSpecialistMapper.offerOfSpecialistMapToDTO(save);
    }
}
