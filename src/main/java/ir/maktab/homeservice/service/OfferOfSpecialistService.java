package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.OfferOfSpecialist;
import ir.maktab.homeservice.dto.CustomerUpdateRequest;
import ir.maktab.homeservice.dto.OfferOfSpecialistRequest;
import ir.maktab.homeservice.service.base.BaseService;

import java.util.List;

public interface OfferOfSpecialistService
        extends BaseService<OfferOfSpecialist, Long> {

    OfferOfSpecialistRequest submitOffer(OfferOfSpecialistRequest request);

    List<OfferOfSpecialistRequest>
    findAllOffersOfSpecialistsByCustomerId(CustomerUpdateRequest request);

    OfferOfSpecialistRequest chooseOfferOfSpecialist(
            OfferOfSpecialistRequest request);

    OfferOfSpecialistRequest startService(OfferOfSpecialistRequest request);

    OfferOfSpecialistRequest endService(OfferOfSpecialistRequest request);
}
