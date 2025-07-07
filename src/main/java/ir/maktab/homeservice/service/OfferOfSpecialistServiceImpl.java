package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.OfferOfSpecialist;
import ir.maktab.homeservice.dto.CustomerSaveUpdateRequest;
import ir.maktab.homeservice.dto.OfferOfSpecialistRequest;
import ir.maktab.homeservice.mapper.OfferOfSpecialistMapper;
import ir.maktab.homeservice.repository.OfferOfSpecialistRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferOfSpecialistServiceImpl
        extends BaseServiceImpl<OfferOfSpecialist, Long, OfferOfSpecialistRepository>
        implements OfferOfSpecialistService {

    private final OfferOfSpecialistMapper offerOfSpecialistMapper;
    private final CustomerService customerService;

    public OfferOfSpecialistServiceImpl(OfferOfSpecialistRepository repository,
                                        OfferOfSpecialistMapper offerOfSpecialistMapper,
                                        CustomerService customerService) {
        super(repository);
        this.offerOfSpecialistMapper = offerOfSpecialistMapper;
        this.customerService = customerService;
    }

    @Override
    public OfferOfSpecialistRequest submitOffer(OfferOfSpecialistRequest request) {
        OfferOfSpecialist offerOfSpecialist = new OfferOfSpecialist();
        offerOfSpecialist.setSuggestedPrice(request.getSuggestedPrice());
        offerOfSpecialist.setStartDateSuggestion(request.getStartDateSuggestion());
        offerOfSpecialist.setTaskDuration(request.getTaskDuration());
        OfferOfSpecialist save = repository.save(offerOfSpecialist);
        return offerOfSpecialistMapper.offerOfSpecialistMapToDTO(save);
    }

    @Override
    public List<OfferOfSpecialistRequest>
    findAllOffersOfSpecialistsByCustomerId(
            CustomerSaveUpdateRequest request) {
        Customer customer = customerService.findById(request.getId()).get();
        return repository.findAllByCustomerIdOrderBySuggestedPriceAsc(customer.getId());
    }
}
