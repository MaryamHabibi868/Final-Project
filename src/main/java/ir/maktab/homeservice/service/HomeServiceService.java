package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.dto.HomeServiceResponse;
import ir.maktab.homeservice.dto.HomeServiceSaveRequest;
import ir.maktab.homeservice.dto.HomeServiceUpdateRequest;
import ir.maktab.homeservice.service.base.BaseService;
import java.util.List;

public interface HomeServiceService extends BaseService<HomeService, Long> {


    HomeServiceResponse createHomeService(
            HomeServiceSaveRequest request);


    HomeServiceResponse updateHomeService(
            HomeServiceUpdateRequest request);


    void deleteHomeService(Long id);


    List<HomeServiceResponse> findAllHomeServices();


    HomeServiceResponse findHomeServiceById(Long id);


    List<HomeServiceResponse> findAllHomeServiceByParentServiceId(Long id);
}
