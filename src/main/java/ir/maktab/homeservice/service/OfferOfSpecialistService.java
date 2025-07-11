package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.OfferOfSpecialist;
import ir.maktab.homeservice.dto.CustomerUpdateRequest;
import ir.maktab.homeservice.dto.OfferOfSpecialistRequest;
import ir.maktab.homeservice.dto.OfferOfSpecialistResponse;
import ir.maktab.homeservice.service.base.BaseService;

import java.util.List;

public interface OfferOfSpecialistService
        extends BaseService<OfferOfSpecialist, Long> {

    //✅ ok
    OfferOfSpecialistResponse submitOfferToOrder(
            OfferOfSpecialistRequest request);

   /* List<OfferOfSpecialistRequest>
    findAllOffersOfSpecialistsByCustomerId(CustomerUpdateRequest request);*/

    //✅ ok
    OfferOfSpecialistResponse chooseOfferOfSpecialist(
            OfferOfSpecialistResponse request);

    //✅ ok
    OfferOfSpecialistResponse startService(OfferOfSpecialistResponse request);

    //✅
    OfferOfSpecialistResponse endService(OfferOfSpecialistResponse request);

    //✅
    List<OfferOfSpecialistResponse> findByOfferOfSpecialistId(
            Long specialistId);
}
