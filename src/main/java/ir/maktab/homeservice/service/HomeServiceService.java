package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.dto.HomeServiceFound;
import ir.maktab.homeservice.dto.HomeServiceSaveUpdateRequest;
import ir.maktab.homeservice.service.base.BaseService;

import java.util.List;

public interface HomeServiceService extends BaseService<HomeService, Long> {

    //✅
    HomeServiceSaveUpdateRequest createHomeService(
            HomeServiceSaveUpdateRequest request);

    //✅
    HomeServiceSaveUpdateRequest updateHomeService(
            HomeServiceSaveUpdateRequest request);

    //✅
    void deleteHomeService(Long id);

    //✅
    List<HomeServiceSaveUpdateRequest> findAllHomeServices();
}
