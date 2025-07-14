package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Offer;
import ir.maktab.homeservice.domains.enumClasses.OfferStatus;
import ir.maktab.homeservice.dto.OfferSaveRequest;
import ir.maktab.homeservice.dto.OfferResponse;
import ir.maktab.homeservice.dto.OrderResponse;
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

    // ☑️ final check
    //✅ ok
    OfferResponse chooseOfferOfSpecialist(
            Long offerId);

    // ☑️ final check
    //✅ ok
    OfferResponse startService(Long offerId);

    // ☑️ final check
    //✅
    OfferResponse endService(Long offerId);

    //✅
    List<OfferResponse> findByOfferOfSpecialistId(
            Long specialistId);

    // ☑️ final check
    //✅
    void paySpecialist(Long offerId);

    // ☑️ final check
    List<OfferResponse> findAllOffersBySuggestedPrice(Long orderId);

    // ☑️ final check
    List<OfferResponse> findAllOffersBySpecialistScore(Long orderId);
    /*//✅
    List<OfferResponse>
    findAllOfferOrderByCustomerId(Long customerId);*/

    // ☑️ final check
    List<OrderResponse> findOrdersBySpecialistId(Long specialistId);
}
