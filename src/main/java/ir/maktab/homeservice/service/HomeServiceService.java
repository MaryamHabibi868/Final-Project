package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.dto.HomeServiceFound;
import ir.maktab.homeservice.dto.HomeServiceSaveUpdateRequest;
import ir.maktab.homeservice.service.base.BaseService;

public interface HomeServiceService extends BaseService<HomeService, Long> {

    void customDeleteHomeServiceById(Long id);

    HomeServiceSaveUpdateRequest createHomeService(
            HomeServiceSaveUpdateRequest request);

    HomeServiceSaveUpdateRequest updateHomeService(
            HomeServiceSaveUpdateRequest request);

    void deleteHomeService(HomeServiceFound request);
}
