package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Manager;
import ir.maktab.homeservice.service.base.BaseService;

public interface ManagerService extends BaseService<Manager, Long> {

    void customDeleteManagerById(Long id);
}
