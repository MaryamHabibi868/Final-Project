package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.service.base.BaseService;

import java.util.List;

public interface SpecialistService extends BaseService<Specialist, Long> {

    //✅
    SpecialistResponse registerSpecialist(
            SpecialistSaveRequest request);

    // ☑️ final check
    //✅
    SpecialistResponse loginSpecialist(
            SpecialistLoginRequest request);

    //✅
    SpecialistResponse updateSpecialistInfo(
            SpecialistUpdateInfo request);

    // ☑️ final check
    //✅
    SpecialistResponse approveSpecialistRegistration(
            Long id);

    // ☑️ final check
    //✅
    void addSpecialistToHomeService(
            Long specialistId, Long homeServiceId);

    // ☑️ final check
    //✅
    void removeSpecialistFromHomeService(
            Long specialistId, Long homeServiceId);

    //✅
    List<SpecialistResponse> findAllSpecialists();

    //✅
    List<SpecialistResponse> findAllByFirstNameContainsIgnoreCaseOrderByIdAsc
    (String firstName);

    //✅
    List<SpecialistResponse> findAllByLastNameContainsIgnoreCaseOrderByIdAsc
    (String lastName);

    //✅
    List<SpecialistResponse> findAllByHomeServiceTitle(
            String homeServiceTitle);
}

