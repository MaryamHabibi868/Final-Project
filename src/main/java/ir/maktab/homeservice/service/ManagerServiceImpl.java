package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Manager;
import ir.maktab.homeservice.repository.ManagerRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;

public class ManagerServiceImpl
        extends BaseServiceImpl<Manager, ManagerRepository>
        implements ManagerService{

    public ManagerServiceImpl(ManagerRepository repository) {
        super(repository);
    }
}
