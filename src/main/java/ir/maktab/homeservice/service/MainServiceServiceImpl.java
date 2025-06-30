package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.MainService;
import ir.maktab.homeservice.repository.MainServiceRepository;

import ir.maktab.homeservice.service.base.BaseServiceImpl;

public class MainServiceServiceImpl
        extends BaseServiceImpl<MainService, MainServiceRepository>
        implements MainServiceService{

    public MainServiceServiceImpl(MainServiceRepository repository) {
        super(repository);
    }
}
