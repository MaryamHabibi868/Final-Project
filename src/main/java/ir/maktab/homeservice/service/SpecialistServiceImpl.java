package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.AccountStatus;
import ir.maktab.homeservice.domains.Manager;
import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.domains.SubService;
import ir.maktab.homeservice.dto.SpecialistSaveUpdateRequest;
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

    public SpecialistServiceImpl(SpecialistRepository specialistRepository,
                                 SpecialistMapper specialistMapper) {
        this.specialistRepository = specialistRepository;
        this.specialistMapper = specialistMapper;
    }

    @Override
    public void deleteSpecialist(Long id) {
        Optional<Specialist> specialistFound = specialistRepository.findById(id);
        if (specialistFound.isPresent()) {
            Specialist specialist = specialistFound.get();
            specialist.setIsActive(false);
            specialistRepository.save(specialist);
        }
        throw new NotFoundException("Specialist Not Found");
    }

    public void approveSpecialistRegistration(SpecialistSaveUpdateRequest request) {
        Optional<Specialist> foundSpecialist = specialistRepository.findById(request.getId());
        if (foundSpecialist.isPresent() &&
                foundSpecialist.get().getAccountStatus() != AccountStatus.APPROVED) {
            Specialist specialist = specialistMapper.specialistDTOMapToEntity(request);
            specialist.setAccountStatus(AccountStatus.APPROVED);
            specialistRepository.save(specialist);
        }
    }

    public void addSpecialistToSubService(Specialist specialist , SubService subService) {
        Optional<Specialist> foundSpecialist = specialistRepository.findById(specialist.getId());
        Optional<SubService> foundSubService = subServiceService.findById(subService.getId());
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
    }
}
