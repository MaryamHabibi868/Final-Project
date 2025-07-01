package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.MainService;
import ir.maktab.homeservice.domains.Manager;
import ir.maktab.homeservice.domains.SubService;
import ir.maktab.homeservice.repository.ManagerRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;

public class ManagerServiceImpl
        extends BaseServiceImpl<Manager, ManagerRepository>
        implements ManagerService{

    public ManagerServiceImpl(ManagerRepository repository,
                              MainService mainService,
                              SubService subService) {
        super(repository);
        this.mainService = mainService;
        this.subService = subService;
    }

    private final MainService mainService;
    private final SubService subService;
}
