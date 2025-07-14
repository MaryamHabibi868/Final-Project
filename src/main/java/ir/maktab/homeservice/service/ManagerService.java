package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Manager;
import ir.maktab.homeservice.dto.ManagerLoginRequest;
import ir.maktab.homeservice.dto.ManagerResponse;
import ir.maktab.homeservice.dto.ManagerSaveRequest;
import ir.maktab.homeservice.dto.ManagerUpdateRequest;
import ir.maktab.homeservice.service.base.BaseService;

public interface ManagerService extends BaseService<Manager, Long> {

    ManagerResponse registerManager(ManagerSaveRequest request);


    ManagerResponse updateManager(ManagerUpdateRequest request);


    ManagerResponse loginManager(ManagerLoginRequest request);
}
