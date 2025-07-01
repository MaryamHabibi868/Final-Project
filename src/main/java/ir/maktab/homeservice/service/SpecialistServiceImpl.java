package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.AccountStatus;
import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.domains.SubService;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.repository.SpecialistRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SpecialistServiceImpl
        extends BaseServiceImpl<Specialist, SpecialistRepository>
        implements SpecialistService {

    public SpecialistServiceImpl(SpecialistRepository repository,
                                 SubServiceService subServiceService) {
        super(repository);

        this.subServiceService = subServiceService;
    }

    private final SubServiceService subServiceService;

    public void approveSpecialistRegistration(Specialist specialist) {
        Optional<Specialist> foundSpecialist = repository.findById(specialist.getId());
        if (foundSpecialist.isPresent() &&
                foundSpecialist.get().getAccountStatus() != AccountStatus.APPROVED) {
            specialist.setAccountStatus(AccountStatus.APPROVED);
            repository.save(specialist);
        }
    }

    public void addSpecialistToSubService(Specialist specialist , SubService subService) {
        Optional<Specialist> foundSpecialist = repository.findById(specialist.getId());
        Optional<SubService> foundSubService = subServiceService.findById(subService.getId());
        if (foundSpecialist.isEmpty()) {
            throw new NotFoundException ("Specialist Not Found");
        }
        if (foundSubService.isEmpty()) {
            throw new NotFoundException ("SubService Not Found");
        }
        foundSpecialist.get().getSubServices().add(subService);
        repository.save(foundSpecialist.get());
    }

    public void removeSpecialistFromSubService(Specialist specialist , SubService subService) {
        Optional<Specialist> foundSpecialist = repository.findById(specialist.getId());
        Optional<SubService> foundSubService = subServiceService.findById(subService.getId());
        if (foundSpecialist.isEmpty()) {
            throw new NotFoundException ("Specialist Not Found");
        }
        if (foundSubService.isEmpty()) {
            throw new NotFoundException ("SubService Not Found");
        }
        foundSpecialist.get().getSubServices().remove(subService);
        repository.save(foundSpecialist.get());
    }
}
