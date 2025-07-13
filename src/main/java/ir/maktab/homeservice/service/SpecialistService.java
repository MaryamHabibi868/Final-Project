package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.service.base.BaseService;

import java.util.List;

public interface SpecialistService extends BaseService<Specialist, Long> {

    //✅
    SpecialistResponse registerSpecialist(
            SpecialistSaveRequest request);

    //✅
    SpecialistResponse loginSpecialist(
            SpecialistLoginRequest request);

    //✅
    SpecialistResponse updateSpecialistInfo(
            SpecialistUpdateInfo request);

    //✅
    SpecialistResponse approveSpecialistRegistration(
            Long id);

    //✅
    void addSpecialistToHomeService(
            Long specialistId, Long homeServiceId);

    //✅
    void removeSpecialistFromHomeService(
            Long specialistId, Long homeServiceId);

    //✅
    List<SpecialistResponse> findAllSpecialists();

    //✅
    List<SpecialistResponse> findAllByFirstNameContainsIgnoreCaseOrderByIdAsc
    (String firstName);

    //✅
    public List<SpecialistResponse> findAllByLastNameContainsIgnoreCaseOrderByIdAsc
    (String lastName);

    List<SpecialistResponse> findAllByHomeServiceTitle(
            String homeServiceTitle);
}

