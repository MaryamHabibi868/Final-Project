package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.MainService;
import ir.maktab.homeservice.domains.SubService;
import ir.maktab.homeservice.dto.MainAndSubServiceSaveUpdateRequest;
import ir.maktab.homeservice.repository.SubServiceRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;

import java.util.Objects;

public class SubServiceServiceImpl
        extends BaseServiceImpl<SubService, SubServiceRepository>
        implements SubServiceService{

    public SubServiceServiceImpl(SubServiceRepository repository, MainService mainService) {
        super(repository);
        this.mainService = mainService;
    }

    private final MainService mainService;

    public SubService createSubService(MainAndSubServiceSaveUpdateRequest mainAndSubServiceSaveUpdateRequest) {
        if (Objects.isNull(mainAndSubServiceSaveUpdateRequest)) {
            return
        }
    }
}
