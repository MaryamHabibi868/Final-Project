package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.OrderOfCustomer;
import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.service.base.BaseService;

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
    SpecialistSaveRequest approveSpecialistRegistration(
            Long id);

    OfferOfSpecialistRequest submitOfferBySpecialist(OfferOfSpecialistRequest request,
                                                     OrderOfCustomer order);

    //✅
    void addSpecialistToHomeService(
            Long specialistId, Long homeServiceId);

    //✅
    void removeSpecialistFromHomeService(
            Long specialistId, Long homeServiceId);
}

