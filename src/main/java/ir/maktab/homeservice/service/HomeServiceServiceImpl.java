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
                repository.findAllByTitleIgnoreCase(
                        request.getTitle());

        if (foundHomeService.isPresent()) {
            throw new DuplicatedException(
                    "Home Service Title Already Exists");
        }
        HomeService homeService = new HomeService();
        homeService.setTitle(request.getTitle());
        homeService.setBasePrice(request.getBasePrice());
        homeService.setDescription(request.getDescription());
        if (request.getParentServiceId() != null) {
            Optional<HomeService> parent = repository.findById(request.getParentServiceId());
            if (parent.isPresent()) {
                homeService.setParentService(parent.get());
            } else {
                throw new NotFoundException("Parent Service Not Found");
            }
        }
        HomeService save = repository.save(homeService);
        return homeServiceMapper.entityMapToResponse(save);
    }

    //✅
    @Transactional
    @Override
    public HomeServiceResponse updateHomeService(
            HomeServiceUpdateRequest request) {
        HomeService foundHomeService = repository
                .findById(request.getId()).orElseThrow(
                        () -> new NotFoundException("Home Service Not Found")
                );
        if (request.getTitle() != null) {
            foundHomeService.setTitle(request.getTitle());
        } else if (request.getBasePrice() != null) {
            foundHomeService.setBasePrice(request.getBasePrice());
        } else if (request.getDescription() != null) {
            foundHomeService.setDescription(request.getDescription());
        } else if (request.getParentServiceId() != null) {
            foundHomeService.setParentService(HomeService.builder()
                    .id(request.getParentServiceId()).build());
        }
        HomeService save = repository.save(foundHomeService);
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
