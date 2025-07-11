package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.*;
import ir.maktab.homeservice.domains.enumClasses.AccountStatus;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.exception.NotApprovedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.SpecialistMapper;
import ir.maktab.homeservice.repository.SpecialistRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (repository.existsByEmail(request.getEmail())){
            throw new DuplicatedException("Email already exists");
        }
        if (repository.existsByPassword(request.getPassword())){
            throw new DuplicatedException("Password already exists");
        }
        Specialist specialist = new Specialist();
        specialist.setFirstName(request.getFirstName());
        specialist.setLastName(request.getLastName());
        specialist.setEmail(request.getEmail());
        specialist.setPassword(request.getPassword());
        specialist.setAccountStatus(AccountStatus.PENDING);
        Specialist save = repository.save(specialist);
        return specialistMapper.entityMapToResponse(save);
    }

    //✅
    //IsActiveTrue?
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
        if (repository.existsByEmail(request.getEmail())){
            throw new DuplicatedException("Email already exists");
        }
        if (repository.existsByPassword(request.getPassword())){
            throw new DuplicatedException("Password already exists");
        }
        specialistFound.setEmail(request.getEmail());
        specialistFound.setPassword(request.getPassword());
        specialistFound.setAccountStatus(AccountStatus.PENDING);
        repository.save(specialistFound);
        return specialistMapper.entityMapToResponse(specialistFound);
    }

    //✅
    @Transactional
    @Override
    public SpecialistSaveRequest approveSpecialistRegistration(Long id) {
        Specialist foundSpecialist = repository.findById(id).orElseThrow(
                () -> new NotFoundException("Specialist Not Found")
        );
        if (foundSpecialist.getAccountStatus() != AccountStatus.APPROVED) {
            foundSpecialist.setAccountStatus(AccountStatus.APPROVED);
            repository.save(foundSpecialist);
        }
        return specialistMapper.specialistMapToDTO(foundSpecialist);
    }

    //✅
    // dto ke ba homeservice rabete dare bayad bezanam? ya hamin doroste?
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

        if (foundSpecialist.getAccountStatus() != AccountStatus.APPROVED) {
            throw new NotApprovedException("This Specialist Not Approved");
        }
        foundSpecialist.getHomeServices().add(foundHomeService);
        repository.save(foundSpecialist);
        homeServiceService.save(foundHomeService);
    }

    //✅
    // soale bala?
    @Override
    @Transactional
    public void removeSpecialistFromHomeService(
            Long specialistId, Long homeServiceId) {
        Specialist foundSpecialist = repository.findById(specialistId).orElseThrow(
                () -> new NotFoundException("Specialist Not Found")
        );
        HomeService foundHomeService = homeServiceService.
                findById(homeServiceId);
        if (foundSpecialist.getAccountStatus() != AccountStatus.APPROVED) {
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
                .map(specialistMapper :: entityMapToResponse)
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
                findAllByFirstNameContainsIgnoreCaseOrderByIdAsc(lastName)
                .stream()
                .map(specialistMapper::entityMapToResponse)
                .toList();
    }

    public List<SpecialistResponse> findAllByHomeServiceTitle(
            String homeServiceTitle) {
       return repository.findAllByHomeServices_HomeServiceTitle(homeServiceTitle).stream()
                .map(specialistMapper::entityMapToResponse)
                .toList();
    }
}
