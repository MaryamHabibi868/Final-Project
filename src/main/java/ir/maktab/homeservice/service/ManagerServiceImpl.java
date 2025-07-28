package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Manager;
import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.domains.enumClasses.Role;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.ManagerMapper;
import ir.maktab.homeservice.repository.ManagerRepository;
import ir.maktab.homeservice.security.SecurityUtil;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class ManagerServiceImpl
        extends BaseServiceImpl<Manager, Long, ManagerRepository>
        implements ManagerService {

    private final ManagerMapper managerMapper;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtil securityUtil;

    public ManagerServiceImpl(ManagerRepository repository,
                              ManagerMapper managerMapper,
                              PasswordEncoder passwordEncoder,
                              SecurityUtil securityUtil) {
        super(repository);
        this.managerMapper = managerMapper;
        this.passwordEncoder = passwordEncoder;
        this.securityUtil = securityUtil;
    }


    @Transactional
    @Override
    public ManagerResponse registerManager(ManagerSaveRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new DuplicatedException("Email address already exist");
        }

        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);

        Manager manager = new Manager();
        manager.setFirstName(request.getFirstName());
        manager.setLastName(request.getLastName());
        manager.setEmail(request.getEmail());
        manager.setPassword(passwordEncoder.encode(request.getPassword()));
        manager.setWallet(wallet);
        wallet.setUserInformation(manager);

        manager.setRole(Role.ROLE_MANAGER);

        manager.setIsActive(true);
        manager.setIsEmailVerify(true);

        Manager save = repository.save(manager);
        return managerMapper.entityMapToResponse(save);
    }


    @Transactional
    @Override
    public ManagerResponse updateManager(ManagerUpdateRequest request) {

        String email = securityUtil.getCurrentUsername();

        Manager foundManager = repository.findByEmail(email)
                .orElseThrow(
                        () -> new NotFoundException("Manager Not Found")
                );


        /*Manager foundManager = repository.findById(request.getId())
                .orElseThrow(
                        () -> new NotFoundException("Manager not found")
                );*/

        if (repository.existsByEmail(request.getEmail())) {
            throw new DuplicatedException("Email address already exist");
        }
        if (request.getFirstName() != null) {
            foundManager.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            foundManager.setLastName(request.getLastName());
        }
        if (request.getEmail() != null) {
            foundManager.setEmail(request.getEmail());
        }
        if (request.getPassword() != null) {
            foundManager.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        Manager save = repository.save(foundManager);
        return managerMapper.entityMapToResponse(save);
    }


    @Override
    public ManagerResponse loginManager(ManagerLoginRequest request) {
        return managerMapper.entityMapToResponse(repository.
                findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new NotFoundException("Manager Not Found")));
    }

}
