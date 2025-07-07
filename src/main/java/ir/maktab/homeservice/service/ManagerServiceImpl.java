package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Manager;
import ir.maktab.homeservice.dto.ManagerFound;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.repository.ManagerRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManagerServiceImpl
        extends BaseServiceImpl<Manager, Long, ManagerRepository>
        implements ManagerService {

    public ManagerServiceImpl(ManagerRepository repository) {
        super(repository);
    }


    @Override
    public void customDeleteManagerById(Long id) {
        Optional<Manager> managerFound = repository.findById(id);
        if (managerFound.isPresent()) {
            Manager manager = managerFound.get();
            manager.setIsActive(false);
            repository.save(manager);
        }
        throw new NotFoundException("Manager Not Found");
    }

    void deleteManager(ManagerFound request) {
        customDeleteManagerById(request.getId());
    }
}
