package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.dto.SpecialistFound;
import ir.maktab.homeservice.dto.SpecialistSaveUpdateRequest;
import ir.maktab.homeservice.dto.SpecialistUpdateInfo;
import ir.maktab.homeservice.service.base.BaseService;

public interface SpecialistService extends BaseService<Specialist, Long> {

    void customDeleteSpecialistById(Long id);

    SpecialistSaveUpdateRequest registerSpecialist(
            SpecialistSaveUpdateRequest request);

    SpecialistSaveUpdateRequest loginSpecialist(
            SpecialistSaveUpdateRequest request);

    SpecialistSaveUpdateRequest updateSpecialistInfo(
            SpecialistUpdateInfo request);

    SpecialistSaveUpdateRequest approveSpecialistRegistration(
            SpecialistFound request);
}

