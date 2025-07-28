package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Offer;
import ir.maktab.homeservice.dto.OfferSaveRequest;
import ir.maktab.homeservice.dto.OfferResponse;
import ir.maktab.homeservice.dto.OrderResponse;
import ir.maktab.homeservice.dto.PaymentResponse;
import ir.maktab.homeservice.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OfferService
        extends BaseService<Offer, Long> {


    OfferResponse submitOfferToOrder(
            OfferSaveRequest request);



    OfferResponse chooseOfferOfSpecialist(
            Long offerId);


    OfferResponse startService(Long offerId);


    OfferResponse endService(Long offerId);


    Page<OfferResponse> findByOfferOfSpecialistId(
            /*Long specialistId,*/ Pageable pageable);


    PaymentResponse paySpecialist(Long offerId);


    Page<OfferResponse> findAllOffersBySuggestedPrice(
            Long orderId, Pageable pageable);


    Page<OfferResponse> findAllOffersBySpecialistScore(
            Long orderId, Pageable pageable);


    Page<OrderResponse> findOrdersBySpecialistId(
            /*Long specialistId,*/ Pageable pageable);
}
