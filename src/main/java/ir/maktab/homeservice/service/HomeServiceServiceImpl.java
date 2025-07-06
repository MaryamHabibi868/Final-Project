package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.HomeService;
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

    private final HomeServiceRepository homeServiceRepository;
    private final HomeServiceMapper homeServiceMapper;

    public HomeServiceServiceImpl(HomeServiceRepository homeServiceRepository, HomeServiceMapper homeServiceMapper) {
        this.homeServiceRepository = homeServiceRepository;
        this.homeServiceMapper = homeServiceMapper;
    }

    public HomeServiceSaveUpdateRequest createMainService(HomeServiceSaveUpdateRequest request) {
       Optional<HomeService> foundMainService =
               homeServiceRepository.findAllByHomeServiceTitleIgnoreCase(request.getMainServiceTitle());
       if (!foundMainService.get().getIsActive()) {
           foundMainService.get().setIsActive(true);
       }
        if (foundMainService.get().getIsActive()) {
            throw new DuplicatedException("Main Service Title Already Exists");
        }
        HomeService homeService = homeServiceMapper.mainServiceDTOMapToEntity(request);
        homeService.setMainServiceTitle(request.getMainServiceTitle());
        HomeService save = homeServiceRepository.save(homeService);
        return homeServiceMapper.mainServiceMapToDTO(save);
    }

    public HomeServiceSaveUpdateRequest updateMainService(HomeServiceSaveUpdateRequest request) {
        Optional<HomeService> foundMainService = homeServiceRepository.findById(request.getId());
        if (foundMainService.isPresent()) {
            HomeService homeService = homeServiceMapper.mainServiceDTOMapToEntity(request);
            homeService.setMainServiceTitle(request.getMainServiceTitle());
            HomeService save = homeServiceRepository.save(homeService);
            return homeServiceMapper.mainServiceMapToDTO(save);
        }
        throw new NotFoundException("Main Service Not Found");
    }

    public void deleteMainService(HomeServiceSaveUpdateRequest request) {
        if (homeServiceRepository.findById(request.getId()).isPresent()) {
            HomeService homeService = homeServiceMapper.mainServiceDTOMapToEntity(request);
            homeServiceRepository.deleteById(homeService.getId());
        }
        throw new NotFoundException("Main Service Not Found");
    }

    public List<HomeService> findAllMainServices() {
        return homeServiceRepository.findAll();
    }

    @Override
    public void deleteHomeService(Long id) {
        Optional<HomeService> homeServiceFound = homeServiceRepository.findById(id);
        if (homeServiceFound.isPresent()) {
            HomeService homeService = homeServiceFound.get();
            homeService.setIsActive(false);
            homeServiceRepository.save(homeService);
        }
        throw new NotFoundException("HomeService Not Found");
    }
}
