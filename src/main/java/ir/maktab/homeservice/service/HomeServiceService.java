package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.service.base.BaseService;

public interface HomeServiceService extends BaseService<HomeService, Long> {

    void customDeleteHomeServiceById(Long id);

}
