package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.*;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.NotActiveException;
import ir.maktab.homeservice.exception.NotApprovedException;
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

    private final SpecialistMapper specialistMapper;
    private final HomeServiceService homeServiceService;
    private final OrderOfCustomerService orderOfCustomerService;
    private final OfferOfSpecialistService offerOfSpecialistService;

    public SpecialistServiceImpl(SpecialistRepository repository,
                                 SpecialistRepository specialistRepository,
                                 SpecialistMapper specialistMapper,
                                 HomeServiceService homeServiceService,
                                 OrderOfCustomerService orderOfCustomerService,
                                 OfferOfSpecialistService offerOfSpecialistService) {
        super(repository);
        this.specialistMapper = specialistMapper;
        this.homeServiceService = homeServiceService;
        this.orderOfCustomerService = orderOfCustomerService;
        this.offerOfSpecialistService = offerOfSpecialistService;
    }

    /*public SpecialistServiceImpl(SpecialistRepository specialistRepository,
                                 SpecialistMapper specialistMapper,
                                 HomeServiceService homeServiceService) {
        this.specialistRepository = specialistRepository;
        this.specialistMapper = specialistMapper;
        this.homeServiceService = homeServiceService;
    }*/

    @Override
    public void customDeleteSpecialistById(Long id) {
        Optional<Specialist> specialistFound = repository.findById(id);
        if (specialistFound.isPresent()) {
            Specialist specialist = specialistFound.get();
            specialist.setIsActive(false);
            repository.save(specialist);
        }
        throw new NotFoundException("Specialist Not Found");
    }

    @Override
   public SpecialistSaveUpdateRequest registerSpecialist(SpecialistSaveUpdateRequest request) {
        Specialist specialist = new Specialist();
        specialist.setFirstName(request.getFirstName());
        specialist.setLastName(request.getLastName());
        specialist.setEmail(request.getEmail());
        specialist.setPassword(request.getPassword());
       Specialist save = repository.save(specialist);
       return specialistMapper.specialistMapToDTO(save);
   }

   @Override
    public SpecialistSaveUpdateRequest loginSpecialist(SpecialistSaveUpdateRequest request) {
        return specialistMapper.specialistMapToDTO(repository.
                findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new NotFoundException("Specialist Not Found")));
    }

    @Override
    public SpecialistSaveUpdateRequest updateSpecialistInfo(SpecialistUpdateInfo request) {
        if (repository.findById(request.getId()).isPresent()) {
            Specialist specialist = specialistMapper.updateInfoMapToEntity(request);
            specialist.setEmail(request.getEmail());
            specialist.setPassword(request.getPassword());
            specialist.setAccountStatus(AccountStatus.PENDING);
            repository.save(specialist);
            return specialistMapper.specialistMapToDTO(specialist);
        }
        throw new NotFoundException("Specialist Not Found");
    }

    public SpecialistSaveUpdateRequest approveSpecialistRegistration(SpecialistFound request) {
        Optional<Specialist> foundSpecialist = repository.findById(request.getId());
        Specialist specialist = specialistMapper.foundSpecialistToEntity(request);
        if (foundSpecialist.isPresent() &&
                foundSpecialist.get().getAccountStatus() != AccountStatus.APPROVED) {
            specialist.setAccountStatus(AccountStatus.APPROVED);
            repository.save(specialist);
        }
        return specialistMapper.specialistMapToDTO(specialist);
    }

    public void addSpecialistToHomeService(SpecialistFound specialist , HomeServiceFound homeService) {
        Optional<Specialist> foundSpecialist = repository.findById(specialist.getId());
        Optional<HomeService> foundHomeService = homeServiceService.findById(homeService.getId());
        if (foundSpecialist.isEmpty()) {
            throw new NotFoundException ("Specialist Not Found");
        }
        if (foundHomeService.isEmpty()) {
            throw new NotFoundException ("HomeService Not Found");
        }
        if (!foundSpecialist.get().getIsActive()){
            throw new NotActiveException("Specialist Not Active");
        }
        if (!foundHomeService.get().getIsActive()){
            throw new NotActiveException("HomeService Not Active");
        }
        if (foundSpecialist.get().getAccountStatus() != AccountStatus.APPROVED) {
            throw new NotApprovedException("This Specialist Not Approved");
        }
        HomeService homeService1 = foundHomeService.get();
        foundSpecialist.get().getHomeServices().add(homeService1);
        repository.save(foundSpecialist.get());
        homeServiceService.save(homeService1);
    }

    public void removeSpecialistFromHomeService(SpecialistFound specialist , HomeServiceFound homeService) {
        Optional<Specialist> foundSpecialist = repository.findById(specialist.getId());
        Optional<HomeService> foundHomeService = homeServiceService.findById(homeService.getId());
        if (foundSpecialist.isEmpty()) {
            throw new NotFoundException ("Specialist Not Found");
        }
        if (foundHomeService.isEmpty()) {
            throw new NotFoundException ("HomeService Not Found");
        }
        if (!foundSpecialist.get().getIsActive()){
            throw new NotActiveException("Specialist Not Active");
        }
        if (!foundHomeService.get().getIsActive()){
            throw new NotActiveException("HomeService Not Active");
        }
        if (foundSpecialist.get().getAccountStatus() != AccountStatus.APPROVED) {
            throw new NotApprovedException("This Specialist Not Approved");
        }
        HomeService homeService1 = foundHomeService.get();
        foundSpecialist.get().getHomeServices().remove(homeService1);
        repository.save(foundSpecialist.get());
        homeServiceService.save(homeService1);
    }

    @Override
    public OfferOfSpecialistRequest submitOfferBySpecialist(OfferOfSpecialistRequest request,
                                        OrderOfCustomer order){
        OrderOfCustomer orderOfCustomerFound = orderOfCustomerService.findById(order.getId()).get();
        if (orderOfCustomerFound
                .getOrderStatus().equals(OrderStatus.WAITING_FOR_SPECIALIST_OFFER)){
            OfferOfSpecialistRequest offerOfSpecialistRequest =
                    offerOfSpecialistService.submitOffer(request);

            orderOfCustomerFound.setOrderStatus(OrderStatus.WAITING_FOR_CHOOSING_SPECIALIST);
            orderOfCustomerService.save(orderOfCustomerFound);

            return offerOfSpecialistRequest;
        }
        throw new NotApprovedException("Order is not waiting for special offer");
    }
}
