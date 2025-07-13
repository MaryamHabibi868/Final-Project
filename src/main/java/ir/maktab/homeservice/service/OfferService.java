package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Offer;
import ir.maktab.homeservice.dto.OfferSaveRequest;
import ir.maktab.homeservice.dto.OfferResponse;
import ir.maktab.homeservice.service.base.BaseService;

import java.util.List;

public interface OfferService
        extends BaseService<Offer, Long> {

    //✅ ok
    OfferResponse submitOfferToOrder(
            OfferSaveRequest request);

   /* List<OfferOfSpecialistRequest>
    findAllOffersOfSpecialistsByCustomerId(CustomerUpdateRequest request);*/

    //✅ ok
    OfferResponse chooseOfferOfSpecialist(
            Long offerId);

    //✅ ok
    OfferResponse startService(Long offerId);

    //✅
    OfferResponse endService(Long offerId);

    //✅
    List<OfferResponse> findByOfferOfSpecialistId(
            Long specialistId);

    //✅
    List<OfferResponse>
    findAllOffeOrderByCustomerId(Long customerId);

    Boolean existsByStatus_AcceptedAndSpecialistIdEquals(Long specialistId);

    Boolean existsByStatus_PendingAndSpecialistIdEquals(Long specialistId);
}
