package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.SubService;
import ir.maktab.homeservice.dto.SubServiceSaveUpdateRequest;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.repository.SubServiceRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import java.util.List;
import java.util.Optional;

public class SubServiceServiceImpl
        extends BaseServiceImpl<SubService, SubServiceRepository>
        implements SubServiceService {

    public SubServiceServiceImpl(SubServiceRepository repository, MainServiceService mainServiceService) {
        super(repository);
        this.mainServiceService = mainServiceService;
    }

    private final MainServiceService mainServiceService;

    /*public SubService createSubService(SubServiceSaveUpdateRequest request) {
        if () {
            SubService subService = new SubService();
            subService.setSubServiceTitle(request.getSubServiceTitle());
            subService.setBasePrice(request.getBasePrice());
            subService.setDescription(request.getDescription());
            return repository.save(subService);
        } else {
            SubService subService = new SubService();
            subService.setMainService(mainServiceService.save(request.getMainService()));
            subService.setSubServiceTitle(request.getSubServiceTitle());
            subService.setBasePrice(request.getBasePrice());
            subService.setDescription(request.getDescription());
            return repository.save(subService);
        }
    }*/


    public SubService updateSubService(SubServiceSaveUpdateRequest request) {
        Optional<SubService> foundSubService = repository.findById(request.getId());
        if (foundSubService.isPresent()) {
            SubService subService = new SubService();
            subService.setSubServiceTitle(request.getSubServiceTitle());
            subService.setMainService(request.getMainService());
            subService.setBasePrice(request.getBasePrice());
            subService.setDescription(request.getDescription());
            return repository.save(subService);
        }
        throw new NotFoundException("Sub Service Not Found");
    }

    public void deleteMainService(SubServiceSaveUpdateRequest request) {
        if (repository.findById(request.getId()).isPresent()) {
            repository.deleteById(request.getId());
        }
        throw new NotFoundException("Sub Service Not Found");
    }

    public List<SubService> findAllSubServices() {
        return repository.findAll();
    }
}
