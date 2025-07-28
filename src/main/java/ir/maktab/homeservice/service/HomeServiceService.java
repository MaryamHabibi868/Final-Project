package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.dto.HomeServiceResponse;
import ir.maktab.homeservice.dto.HomeServiceSaveRequest;
import ir.maktab.homeservice.dto.HomeServiceUpdateRequest;
import ir.maktab.homeservice.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface HomeServiceService extends BaseService<HomeService, Long> {


    HomeServiceResponse createHomeService(
            HomeServiceSaveRequest request);


    HomeServiceResponse updateHomeService(
            HomeServiceUpdateRequest request);


    void deleteHomeService(Long id);


    Page<HomeServiceResponse> findAllHomeServices(Pageable pageable);


    HomeServiceResponse findHomeServiceById(Long id);


    Page<HomeServiceResponse> findAllHomeServiceByParentServiceId(
            Long id, Pageable pageable);
}
