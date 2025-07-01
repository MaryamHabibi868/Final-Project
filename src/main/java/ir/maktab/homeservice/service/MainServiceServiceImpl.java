package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.MainService;
import ir.maktab.homeservice.dto.MainServiceSaveUpdateRequest;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.repository.MainServiceRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MainServiceServiceImpl
        extends BaseServiceImpl<MainService, MainServiceRepository>
        implements MainServiceService {

    public MainServiceServiceImpl(MainServiceRepository repository) {
        super(repository);
    }

    public MainService createMainService(MainServiceSaveUpdateRequest request) {
       Optional<MainService> foundMainService = repository.findByMainServiceTitle(request.getMainServiceTitle());
        if (foundMainService.isPresent()) {
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
}
