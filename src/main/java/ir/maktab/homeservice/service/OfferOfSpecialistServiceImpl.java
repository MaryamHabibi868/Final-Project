package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.OfferOfSpecialist;
import ir.maktab.homeservice.dto.OfferOfSpecialistRequest;
import ir.maktab.homeservice.mapper.OfferOfSpecialistMapper;
import ir.maktab.homeservice.repository.OfferOfSpecialistRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OfferOfSpecialistServiceImpl
        extends BaseServiceImpl<OfferOfSpecialist, Long, OfferOfSpecialistRepository>
        implements OfferOfSpecialistService {

    private final OfferOfSpecialistMapper offerOfSpecialistMapper;

    public OfferOfSpecialistServiceImpl(OfferOfSpecialistRepository repository, OfferOfSpecialistMapper offerOfSpecialistMapper) {
        super(repository);
        this.offerOfSpecialistMapper = offerOfSpecialistMapper;
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
}
