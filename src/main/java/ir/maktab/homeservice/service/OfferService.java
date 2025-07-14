package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Offer;
import ir.maktab.homeservice.domains.enumClasses.OfferStatus;
import ir.maktab.homeservice.dto.OfferSaveRequest;
import ir.maktab.homeservice.dto.OfferResponse;
import ir.maktab.homeservice.service.base.BaseService;

import java.util.List;

public interface OfferService
        extends BaseService<Offer, Long> {

    // ☑️ final check
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
    void paySpecialist(Long offerId);

    // ☑️ final check
    List<OfferResponse> findAllOffersBySuggestedPrice(Long orderId);

    // ☑️ final check
    List<OfferResponse> findAllOffersBySpecialistScore(Long orderId);
    /*//✅
    List<OfferResponse>
    findAllOfferOrderByCustomerId(Long customerId);*/

}
