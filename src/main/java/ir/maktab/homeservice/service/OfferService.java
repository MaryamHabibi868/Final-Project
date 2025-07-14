package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Offer;
import ir.maktab.homeservice.dto.OfferSaveRequest;
import ir.maktab.homeservice.dto.OfferResponse;
import ir.maktab.homeservice.dto.OrderResponse;
import ir.maktab.homeservice.service.base.BaseService;
import java.util.List;

public interface OfferService
        extends BaseService<Offer, Long> {


    OfferResponse submitOfferToOrder(
            OfferSaveRequest request);



    OfferResponse chooseOfferOfSpecialist(
            Long offerId);


    OfferResponse startService(Long offerId);


    OfferResponse endService(Long offerId);


    List<OfferResponse> findByOfferOfSpecialistId(
            Long specialistId);


    void paySpecialist(Long offerId);


    List<OfferResponse> findAllOffersBySuggestedPrice(Long orderId);


    List<OfferResponse> findAllOffersBySpecialistScore(Long orderId);


    List<OrderResponse> findOrdersBySpecialistId(Long specialistId);
}
