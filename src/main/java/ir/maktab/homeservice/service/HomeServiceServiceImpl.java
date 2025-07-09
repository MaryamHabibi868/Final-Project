package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.dto.HomeServiceResponse;
import ir.maktab.homeservice.dto.HomeServiceSaveRequest;
import ir.maktab.homeservice.dto.HomeServiceUpdateRequest;
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

    //✅
    @Override
    public HomeServiceResponse createHomeService(
            HomeServiceSaveRequest request) {
        Optional<HomeService> foundHomeService =
                repository.findAllByHomeServiceTitleIgnoreCase(
                        request.getHomeServiceTitle());
        if (!foundHomeService.get().getIsActive()) {
            foundHomeService.get().setIsActive(true);
        }
        if (foundHomeService.get().getIsActive()) {
            throw new DuplicatedException("Home Service Title Already Exists");
        }
        HomeService homeService = homeServiceMapper.saveRequestMapToEntity(request);
        homeService.setHomeServiceTitle(request.getHomeServiceTitle());
        homeService.setBasePrice(request.getBasePrice());
        homeService.setDescription(request.getDescription());
        homeService.setParentService(request.getParentService());
        HomeService save = repository.save(homeService);
        return homeServiceMapper.entityMapToResponse(save);
    }

    //✅
    @Override
    public HomeServiceResponse updateHomeService(
            HomeServiceUpdateRequest request) {
        Optional<HomeService> foundMainService = repository.findById(request.getId());
        if (foundMainService.isPresent()) {
            HomeService homeService = homeServiceMapper.updateRequestMapToEntity(request);
            homeService.setHomeServiceTitle(request.getHomeServiceTitle());
            homeService.setBasePrice(request.getBasePrice());
            homeService.setDescription(request.getDescription());
            homeService.setParentService(request.getParentService());
            HomeService save = repository.save(homeService);
            return homeServiceMapper.entityMapToResponse(save);
        }
        throw new NotFoundException("Home Service Not Found");
    }

    //✅
    @Override
    public void deleteHomeService(Long id) {
        customDeleteById(id);
    }

    //✅
    @Override
    public List<HomeServiceResponse> findAllHomeServices() {
       return repository.findAllByIsActiveTrue().stream()
                .map(homeServiceMapper::entityMapToResponse)
                .toList();
    }
}
