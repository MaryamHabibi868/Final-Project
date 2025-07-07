package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.dto.HomeServiceFound;
import ir.maktab.homeservice.dto.HomeServiceSaveUpdateRequest;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.HomeServiceMapper;
import ir.maktab.homeservice.repository.HomeServiceRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HomeServiceServiceImpl
        extends BaseServiceImpl<HomeService, Long, HomeServiceRepository>
        implements HomeServiceService {

    private final HomeServiceMapper homeServiceMapper;

    public HomeServiceServiceImpl(HomeServiceRepository repository,
                                  HomeServiceMapper homeServiceMapper) {
        super(repository);
        this.homeServiceMapper = homeServiceMapper;
    }


    public HomeServiceSaveUpdateRequest createHomeService(HomeServiceSaveUpdateRequest request) {
       Optional<HomeService> foundHomeService =
               repository.findAllByHomeServiceTitleIgnoreCase(
                       request.getHomeServiceTitle());
       if (!foundHomeService.get().getIsActive()) {
           foundHomeService.get().setIsActive(true);
       }
        if (foundHomeService.get().getIsActive()) {
            throw new DuplicatedException("Home Service Title Already Exists");
        }
        HomeService homeService = homeServiceMapper.homeServiceDTOMapToEntity(request);
        homeService.setHomeServiceTitle(request.getHomeServiceTitle());
        homeService.setBasePrice(request.getBasePrice());
        homeService.setDescription(request.getDescription());
        HomeService save = repository.save(homeService);
        return homeServiceMapper.homeServiceMapToDTO(save);
    }

    public HomeServiceSaveUpdateRequest updateMainService(HomeServiceSaveUpdateRequest request) {
        Optional<HomeService> foundMainService = repository.findById(request.getId());
        if (foundMainService.isPresent()) {
            HomeService homeService = homeServiceMapper.homeServiceDTOMapToEntity(request);
            homeService.setHomeServiceTitle(request.getHomeServiceTitle());
            homeService.setBasePrice(request.getBasePrice());
            homeService.setDescription(request.getDescription());
            HomeService save = repository.save(homeService);
            return homeServiceMapper.homeServiceMapToDTO(save);
        }
        throw new NotFoundException("Main Service Not Found");
    }

    public void deleteHomeService(HomeServiceFound request) {
        customDeleteHomeServiceById(request.getId());
    }

    @Override
    public void customDeleteHomeServiceById(Long id) {
        Optional<HomeService> homeServiceFound = repository.findById(id);
        if (homeServiceFound.isPresent()) {
            HomeService homeService = homeServiceFound.get();
            homeService.setIsActive(false);
            repository.save(homeService);
        }
        throw new NotFoundException("HomeService Not Found");
    }
}
