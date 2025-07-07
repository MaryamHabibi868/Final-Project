package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.OfferOfSpecialist;
import ir.maktab.homeservice.dto.OfferOfSpecialistRequest;
import ir.maktab.homeservice.service.base.BaseService;

public interface OfferOfSpecialistService
        extends BaseService<OfferOfSpecialist, Long> {

    OfferOfSpecialistRequest submitOffer(OfferOfSpecialistRequest request);
}
