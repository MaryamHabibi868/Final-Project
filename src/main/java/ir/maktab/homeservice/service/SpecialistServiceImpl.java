package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.*;
import ir.maktab.homeservice.domains.enumClasses.AccountStatus;
import ir.maktab.homeservice.domains.enumClasses.OfferStatus;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.exception.NotApprovedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.SpecialistMapper;
import ir.maktab.homeservice.repository.SpecialistRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class SpecialistServiceImpl
        extends BaseServiceImpl<Specialist, Long, SpecialistRepository>
        implements SpecialistService {

    private final SpecialistMapper specialistMapper;
    private final HomeServiceService homeServiceService;

    public SpecialistServiceImpl(SpecialistRepository repository,
                                 SpecialistMapper specialistMapper,
                                 HomeServiceService homeServiceService) {
        super(repository);
        this.specialistMapper = specialistMapper;
        this.homeServiceService = homeServiceService;
    }

    //✅
    // IsActiveTrue?
    @Transactional
    @Override
    public SpecialistResponse registerSpecialist(SpecialistSaveRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new DuplicatedException("Email already exists");
        }

        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);

        Specialist specialist = new Specialist();
        specialist.setFirstName(request.getFirstName());
        specialist.setLastName(request.getLastName());
        specialist.setEmail(request.getEmail());
        specialist.setPassword(request.getPassword());

        if (request.getProfileImagePath() != null) {
            specialist.setProfileImagePath(request.getProfileImagePath());
            /*if ((request.getProfileImagePath()).getSize() > 300 * 1024 ){
                throw new NotApprovedException("Profile image is too large");
            }*/
            specialist.setStatus(AccountStatus.NEW);
        } else {
            specialist.setStatus(AccountStatus.PENDING);
        }
        specialist.setScore(0.0);
        specialist.setWallet(wallet);
        wallet.setUserInformation(specialist);

        Specialist save = repository.save(specialist);
        return specialistMapper.entityMapToResponse(save);
    }

    // ☑️ final check
    //✅
    @Override
    public SpecialistResponse loginSpecialist(
            SpecialistLoginRequest request) {
        return specialistMapper.entityMapToResponse(repository.
                findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new NotFoundException("Specialist Not Found")));
    }

    //✅
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

    // ☑️ final check
    //✅
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

    // ☑️ final check
    //✅
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

    // ☑️ final check
    //✅
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


    //✅
    @Override
    public List<SpecialistResponse> findAllSpecialists() {
        return repository.findUsersByIdNotNull(Specialist.class).stream()
                .map(specialistMapper::entityMapToResponse)
                .toList();
    }

    //✅
    @Override
    public List<SpecialistResponse> findAllByFirstNameContainsIgnoreCaseOrderByIdAsc
    (String firstName) {
        return repository.
                findAllByFirstNameContainsIgnoreCaseOrderByIdAsc(firstName)
                .stream()
                .map(specialistMapper::entityMapToResponse)
                .toList();
    }

    //✅
    @Override
    public List<SpecialistResponse> findAllByLastNameContainsIgnoreCaseOrderByIdAsc
    (String lastName) {
        return repository.
                findAllByLastNameContainsIgnoreCaseOrderByIdAsc(lastName)
                .stream()
                .map(specialistMapper::entityMapToResponse)
                .toList();
    }

    //✅
    @Override
    public List<SpecialistResponse> findAllByHomeServiceTitle(
            String homeServiceTitle) {
        return repository.findAllByHomeServices_title(homeServiceTitle).stream()
                .map(specialistMapper::entityMapToResponse)
                .toList();
    }
}
