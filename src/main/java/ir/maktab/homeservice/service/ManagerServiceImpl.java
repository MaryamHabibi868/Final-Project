package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.Manager;
import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.ManagerMapper;
import ir.maktab.homeservice.repository.ManagerRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ManagerServiceImpl
        extends BaseServiceImpl<Manager, Long, ManagerRepository>
        implements ManagerService {

    private final ManagerMapper managerMapper;

    public ManagerServiceImpl(ManagerRepository repository, ManagerMapper managerMapper) {
        super(repository);
        this.managerMapper = managerMapper;
    }

    //✅
    @Transactional
    @Override
    public ManagerResponse registerManager(ManagerSaveRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new DuplicatedException("Email address already exist");
        }
        Manager manager = new Manager();
        manager.setFirstName(request.getFirstName());
        manager.setLastName(request.getLastName());
        manager.setEmail(request.getEmail());
        manager.setPassword(request.getPassword());
        manager.setWallet(Wallet.builder().balance(BigDecimal.ZERO).build());
        Manager save = repository.save(manager);
        return managerMapper.entityMapToResponse(save);
    }

    //✅
    @Transactional
    @Override
    public ManagerResponse updateManager(ManagerUpdateRequest request) {
        Manager foundManager = repository.findById(request.getId())
                .orElseThrow(
                        () -> new NotFoundException("Manager not found")
                );
        if (repository.existsByEmail(request.getEmail())) {
            throw new DuplicatedException("Email address already exist");
        }
        if (request.getFirstName() != null) {
            foundManager.setFirstName(request.getFirstName());
        } else if (request.getLastName() != null) {
            foundManager.setLastName(request.getLastName());
        } else if (request.getEmail() != null) {
            foundManager.setEmail(request.getEmail());
        } else if (request.getPassword() != null) {
            foundManager.setPassword(request.getPassword());
        }
        Manager save = repository.save(foundManager);
        return managerMapper.entityMapToResponse(save);
    }

    //✅
    @Override
    public ManagerResponse loginManager(ManagerLoginRequest request) {
        return managerMapper.entityMapToResponse(repository.
                findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new NotFoundException("Manager Not Found")));
    }

}
