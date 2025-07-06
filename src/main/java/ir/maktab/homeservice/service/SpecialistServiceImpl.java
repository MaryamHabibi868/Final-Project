package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.AccountStatus;
import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.domains.Manager;
import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.domains.SubService;
import ir.maktab.homeservice.dto.HomeServiceFound;
import ir.maktab.homeservice.dto.SpecialistFound;
import ir.maktab.homeservice.dto.SpecialistSaveUpdateRequest;
import ir.maktab.homeservice.dto.SpecialistUpdateInfo;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.SpecialistMapper;
import ir.maktab.homeservice.repository.SpecialistRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SpecialistServiceImpl
        extends BaseServiceImpl<Specialist, Long, SpecialistRepository>
        implements SpecialistService {

    private final SpecialistRepository specialistRepository;
    private final SpecialistMapper specialistMapper;
    private final HomeServiceService homeServiceService;

    public SpecialistServiceImpl(SpecialistRepository specialistRepository,
                                 SpecialistMapper specialistMapper,
                                 HomeServiceService homeServiceService) {
        this.specialistRepository = specialistRepository;
        this.specialistMapper = specialistMapper;
        this.homeServiceService = homeServiceService;
    }

    @Override
    public void customDeleteSpecialistById(Long id) {
        Optional<Specialist> specialistFound = specialistRepository.findById(id);
        if (specialistFound.isPresent()) {
            Specialist specialist = specialistFound.get();
            specialist.setIsActive(false);
            specialistRepository.save(specialist);
        }
        throw new NotFoundException("Specialist Not Found");
    }

   public SpecialistSaveUpdateRequest registerSpecialist(SpecialistSaveUpdateRequest request) {
        Specialist specialist = new Specialist();
        specialist.setFirstName(request.getFirstName());
        specialist.setLastName(request.getLastName());
        specialist.setEmail(request.getEmail());
        specialist.setPassword(request.getPassword());
       Specialist save = specialistRepository.save(specialist);
       return specialistMapper.specialistMapToDTO(save);
   }

    public SpecialistSaveUpdateRequest loginSpecialist(SpecialistSaveUpdateRequest request) {
        return specialistMapper.specialistMapToDTO(specialistRepository.
                findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new NotFoundException("Specialist Not Found")));
    }

    public SpecialistSaveUpdateRequest updateSpecialistInfo(SpecialistUpdateInfo request) {
        Specialist specialist = specialistMapper.updateInfoMapToEntity(request);
        specialist.setEmail(request.getEmail());
        specialist.setPassword(request.getPassword());
        specialist.setAccountStatus(AccountStatus.PENDING);
        specialistRepository.save(specialist);
        return specialistMapper.specialistMapToDTO(specialist);
    }

    public SpecialistSaveUpdateRequest approveSpecialistRegistration(SpecialistFound request) {
        Optional<Specialist> foundSpecialist = specialistRepository.findById(request.getId());
        Specialist specialist = specialistMapper.foundSpecialistToEntity(request);
        if (foundSpecialist.isPresent() &&
                foundSpecialist.get().getAccountStatus() != AccountStatus.APPROVED) {
            specialist.setAccountStatus(AccountStatus.APPROVED);
            specialistRepository.save(specialist);
        }
        return specialistMapper.specialistMapToDTO(specialist);
    }

   /* public void addSpecialistToHomeService(SpecialistFound specialist , HomeServiceFound homeService) {
        Optional<Specialist> foundSpecialist = specialistRepository.findById(specialist.getId());
        Optional<HomeService> foundHomeService = homeServiceService.
        if (foundSpecialist.isEmpty()) {
            throw new NotFoundException ("Specialist Not Found");
        }
        if (foundSubService.isEmpty()) {
            throw new NotFoundException ("SubService Not Found");
        }
        foundSpecialist.get().getSubServices().add(subService);
        specialistRepository.save(foundSpecialist.get());
    }

    public void removeSpecialistFromSubService(Specialist specialist , SubService subService) {
        Optional<Specialist> foundSpecialist = specialistRepository.findById(specialist.getId());
        Optional<SubService> foundSubService = subServiceService.findById(subService.getId());
        if (foundSpecialist.isEmpty()) {
            throw new NotFoundException ("Specialist Not Found");
        }
        if (foundSubService.isEmpty()) {
            throw new NotFoundException ("SubService Not Found");
        }
        foundSpecialist.get().getSubServices().remove(subService);
        specialistRepository.save(foundSpecialist.get());
    }*/
}
