package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.OrderOfCustomer;
import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.dto.*;
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

    OfferOfSpecialistRequest submitOfferBySpecialist(OfferOfSpecialistRequest request,
                                                     OrderOfCustomer order);

    void addSpecialistToHomeService(
            SpecialistFound specialist ,
            HomeServiceFound homeService);

    void removeSpecialistFromHomeService(
            SpecialistFound specialist,
            HomeServiceFound homeService);
}

