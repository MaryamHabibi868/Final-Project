package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.Manager;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.repository.ManagerRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManagerServiceImpl
        extends BaseServiceImpl<Manager, Long, ManagerRepository>
        implements ManagerService{

    private final ManagerRepository managerRepository;

    public ManagerServiceImpl(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public void deleteManager(Long id) {
        Optional<Manager> managerFound = managerRepository.findById(id);
        if (managerFound.isPresent()) {
            Manager manager = managerFound.get();
            manager.setIsActive(false);
            managerRepository.save(manager);
        }
        throw new NotFoundException("Manager Not Found");
    }

}
