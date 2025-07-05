package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.MainService;
import ir.maktab.homeservice.dto.MainServiceSaveUpdateRequest;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.repository.MainServiceRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MainServiceServiceImpl
        extends BaseServiceImpl<MainService, Long, MainServiceRepository>
        implements MainServiceService {

    public MainServiceServiceImpl(MainServiceRepository repository) {
        super(repository);
    }

    public MainService createMainService(MainServiceSaveUpdateRequest request) {
       Optional<MainService> foundMainService = repository.findByMainServiceTitle(request.getMainServiceTitle());
       if (!foundMainService.get().getActive()) {
           foundMainService.get().setActive(true);
       }
        if (foundMainService.isPresent() && foundMainService.get().getActive()) {
            throw new DuplicatedException("Main Service Title Already Exists");
        }
        MainService mainService = new MainService();
        mainService.setMainServiceTitle(request.getMainServiceTitle());
        return repository.save(mainService);
    }

    public MainService updateMainService(MainServiceSaveUpdateRequest request) {
        Optional<MainService> foundMainService = repository.findById(request.getId());
        if (foundMainService.isPresent()) {
            MainService mainService = new MainService();
            mainService.setMainServiceTitle(request.getMainServiceTitle());
           return repository.save(mainService);
        }
        throw new NotFoundException("Main Service Not Found");
    }

    public void deleteMainService(MainServiceSaveUpdateRequest request) {
        if (repository.findById(request.getId()).isPresent()) {
            repository.deleteById(request.getId());
        }
        throw new NotFoundException("Main Service Not Found");
    }

    public List<MainService> findAllMainServices() {
        return repository.findAll();
    }
}
