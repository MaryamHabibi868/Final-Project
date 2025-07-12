package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.dto.HomeServiceResponse;
import ir.maktab.homeservice.dto.HomeServiceSaveRequest;
import ir.maktab.homeservice.dto.HomeServiceUpdateRequest;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.HomeServiceMapper;
import ir.maktab.homeservice.repository.HomeServiceRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    @Override
    public HomeServiceResponse createHomeService(
            HomeServiceSaveRequest request) {
        Optional<HomeService> foundHomeService =
                repository.findAllByHomeServiceTitleIgnoreCase(
                        request.getHomeServiceTitle());

                        /*.orElseThrow(
                        () -> new NotFoundException(
                                "Home Service Title Already Exists")
                );*/
        if (foundHomeService.isPresent()) {
            throw new NotFoundException(
                    "Home Service Title Already Exists");
        }
        HomeService homeService = new HomeService();
        homeService.setHomeServiceTitle(request.getHomeServiceTitle());
        homeService.setBasePrice(request.getBasePrice());
        homeService.setDescription(request.getDescription());
        homeService.setParentService(HomeService.builder()
                .id(request.getParentServiceId()).build());

        HomeService save = repository.save(homeService);
        return homeServiceMapper.entityMapToResponse(save);
    }

    //✅
    @Transactional
    @Override
    public HomeServiceResponse updateHomeService(
            HomeServiceUpdateRequest request) {
        HomeService foundMainService = repository
                .findById(request.getId()).orElseThrow(
                        () -> new NotFoundException("Home Service Not Found")
                );
        HomeService homeService = homeServiceMapper.updateRequestMapToEntity(request);
        homeService.setHomeServiceTitle(request.getHomeServiceTitle());
        homeService.setBasePrice(request.getBasePrice());
        homeService.setDescription(request.getDescription());
        homeService.setParentService(HomeService.builder()
                .id(request.getParentServiceId()).build());
        HomeService save = repository.save(homeService);
        return homeServiceMapper.entityMapToResponse(save);
    }

    //✅
    @Transactional
    @Override
    public void deleteHomeService(Long id) {
        deleteById(id);
    }

    //✅
    @Override
    public List<HomeServiceResponse> findAllHomeServices() {
        return repository.findAll().stream()
                .map(homeServiceMapper::entityMapToResponse)
                .toList();
    }
}
