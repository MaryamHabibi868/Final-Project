package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.*;
import ir.maktab.homeservice.domains.enumClasses.AccountStatus;
import ir.maktab.homeservice.domains.enumClasses.OfferStatus;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.exception.NotApprovedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.HomeServiceMapper;
import ir.maktab.homeservice.mapper.SpecialistMapper;
import ir.maktab.homeservice.mapper.TransactionMapper;
import ir.maktab.homeservice.repository.SpecialistRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class SpecialistServiceImpl
        extends BaseServiceImpl<Specialist, Long, SpecialistRepository>
        implements SpecialistService {

    private final SpecialistMapper specialistMapper;
    private final HomeServiceService homeServiceService;
    private final HomeServiceMapper homeServiceMapper;
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;
    private final RoleService roleService;

    public SpecialistServiceImpl(SpecialistRepository repository,
                                 SpecialistMapper specialistMapper,
                                 HomeServiceService homeServiceService,
                                 HomeServiceMapper homeServiceMapper,
                                 TransactionService transactionService,
                                 TransactionMapper transactionMapper,
                                 RoleService roleService) {
        super(repository);
        this.specialistMapper = specialistMapper;
        this.homeServiceService = homeServiceService;
        this.homeServiceMapper = homeServiceMapper;
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
        this.roleService = roleService;
    }


    @Transactional
    @Override
    public SpecialistResponse registerSpecialist(SpecialistSaveRequest request) {

        Optional<Specialist> existingSpecialist =
                repository.findByEmail(request.getEmail());

        if (existingSpecialist.isPresent()) {
            Specialist registeredSpecialist = existingSpecialist.get();
            if (registeredSpecialist.getIsEmailVerify()) {
                throw new DuplicatedException("Email already exists");
            }
        }

        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);

        Specialist specialist = new Specialist();
        specialist.setFirstName(request.getFirstName());
        specialist.setLastName(request.getLastName());
        specialist.setEmail(request.getEmail());
        specialist.setPassword(request.getPassword());

        Role role = roleService.findByName("SPECIALIST");
        specialist.setRole(role);


        if (request.getProfileImagePath() != null) {
            specialist.setProfileImagePath(request.getProfileImagePath());
                /*if ((request.getProfileImagePath()).getSize() > 300 * 1024 ){
                throw new NotApprovedException("Profile image is too large");
            }*/
        }

        specialist.setStatus(AccountStatus.NEW);

        specialist.setScore(0.0);
        specialist.setWallet(wallet);
        wallet.setUserInformation(specialist);

        Specialist save = repository.save(specialist);

        sendVerificationEmail(save);

        return specialistMapper.entityMapToResponse(save);


    }


    public void sendVerificationEmail(Specialist specialist) {
        System.out.println("Please click on your email for verification " +
                repository.findByEmail(specialist.getEmail()));
    }

    public SpecialistResponse verifySpecialist(Specialist specialist) {
        Optional<Specialist> specialist1 = repository.findByEmail(specialist.getEmail());
        if (specialist1.isEmpty()) {
            throw new NotFoundException("Specialist not found");
        }
        if (specialist1.get().getIsEmailVerify()) {
            throw new NotApprovedException("This specialist is already verified ");
        }

        Specialist specialist2 = specialist1.get();
        specialist2.setIsEmailVerify(true);
        specialist2.setIsActive(true);

        if (specialist.getProfileImagePath() != null) {
            specialist2.setStatus(AccountStatus.PENDING);
        }


        Specialist save = repository.save(specialist2);

        return specialistMapper.entityMapToResponse(save);
    }

    @Override
    public SpecialistResponse loginSpecialist(
            SpecialistLoginRequest request) {
        return specialistMapper.entityMapToResponse(repository.
                findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new NotFoundException("Specialist Not Found")));
    }


    @Transactional
    @Override
    public SpecialistResponse updateSpecialistInfo(SpecialistUpdateInfo request) {
        Specialist specialistFound = repository.findById(request.getId())
                .orElseThrow(
                        () -> new NotFoundException("Specialist Not Found")
                );

        Long specialistId = specialistFound.getId();
        if (repository.existsByOffersStatusAndId(
                OfferStatus.ACCEPTED, specialistId)) {
            throw new NotApprovedException("Specialist has active offers");
        }
        if (repository.existsByOffersStatusAndId(
                OfferStatus.PENDING, specialistId)) {
            throw new NotApprovedException("Specialist has pending offers");
        }

        if (repository.existsByEmail(request.getEmail())) {
            throw new DuplicatedException("Email already exists");
        }
        if (request.getEmail() != null) {
            specialistFound.setEmail(request.getEmail());
        }
        if (request.getPassword() != null) {
            specialistFound.setPassword(request.getPassword());
        }
        if (request.getProfileImagePath() != null) {
            specialistFound.setProfileImagePath(request.getProfileImagePath());
        }
        specialistFound.setStatus(AccountStatus.PENDING);
        repository.save(specialistFound);
        return specialistMapper.entityMapToResponse(specialistFound);
    }


    @Transactional
    @Override
    public SpecialistResponse approveSpecialistRegistration(Long id) {
        Specialist foundSpecialist = repository.findById(id).orElseThrow(
                () -> new NotFoundException("Specialist Not Found")
        );
        if (foundSpecialist.getStatus() != AccountStatus.APPROVED) {
            foundSpecialist.setStatus(AccountStatus.APPROVED);
            repository.save(foundSpecialist);
        }
        return specialistMapper.entityMapToResponse(foundSpecialist);
    }


    @Transactional
    @Override
    public void addSpecialistToHomeService(
            Long specialistId, Long homeServiceId) {

        Specialist foundSpecialist = repository.findById(specialistId)
                .orElseThrow(
                        () -> new NotFoundException("Specialist Not Found")
                );
        HomeService foundHomeService = homeServiceService.
                findById(homeServiceId);

        if (foundSpecialist.getStatus() != AccountStatus.APPROVED) {
            throw new NotApprovedException("This Specialist Not Approved");
        }
        foundSpecialist.getHomeServices().add(foundHomeService);
        repository.save(foundSpecialist);
        homeServiceService.save(foundHomeService);
    }


    @Override
    @Transactional
    public void removeSpecialistFromHomeService(
            Long specialistId, Long homeServiceId) {
        Specialist foundSpecialist = repository.findById(specialistId).orElseThrow(
                () -> new NotFoundException("Specialist Not Found")
        );
        HomeService foundHomeService = homeServiceService.
                findById(homeServiceId);
        if (foundSpecialist.getStatus() != AccountStatus.APPROVED) {
            throw new NotApprovedException("This Specialist Not Approved");
        }
        foundSpecialist.getHomeServices().remove(foundHomeService);
        repository.save(foundSpecialist);
        homeServiceService.save(foundHomeService);
    }


    @Override
    public Page<HomeServiceResponse> findAllHomeServicesBySpecialistId(
            Long specialistId, Pageable pageable) {
        return repository.findHomeServicesBySpecialistId(specialistId, pageable)
                .map(homeServiceMapper::entityMapToResponse);
    }


    @Override
    public Page<SpecialistResponse> findAllByHomeServiceId(
            Long homeServiceId, Pageable pageable) {
        return repository.findAllByHomeServices_id(homeServiceId, pageable)
                .map(specialistMapper::entityMapToResponse);
    }

    @Override
    public Page<SpecialistResponse> findAllByScoreIsBetween(
            Double lower, Double higher, Pageable pageable) {
        return repository.findAllByScoreIsBetween(lower, higher, pageable)
                .map(specialistMapper::entityMapToResponse);
    }


    @Override
    public Double findScoreBySpecialistId(Long specialistId) {
        Specialist foundSpecialist = repository.findById(specialistId).orElseThrow(
                () -> new NotFoundException("Specialist Not Found"));

        return foundSpecialist.getScore();
    }


    @Override
    public Page<TransactionResponse> findAllTransactionsBySpecialistId(
            Long specialistId, Pageable pageable) {
        Specialist foundSpecialist = repository.findById(specialistId).orElseThrow(
                () -> new NotFoundException("Specialist Not Found")
        );

        Long walletId = foundSpecialist.getWallet().getId();
        return transactionService.findAllByWalletId(walletId, pageable)
                .map(transactionMapper::entityMapToResponse);
    }


    @Override
    public void inActivateSpecialist() {
        repository.findAllByScoreIsLessThan(0.0).forEach(specialist -> {
            specialist.setStatus(AccountStatus.INACTIVE);
        });
    }
}
