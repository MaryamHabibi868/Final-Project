package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.Offer;
import ir.maktab.homeservice.domains.Order;
import ir.maktab.homeservice.domains.enumClasses.OfferStatus;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository
        extends BaseRepository<Offer, Long> {

    @Query
    List<Offer> findAllBySpecialistId(Long specialist_id);


    // ☑️ final check
    List<Offer> findAllByOrderInformation_Id(Long order_id);

    // ☑️ final check
    List<Offer> findAllByOrderInformation_Id(Long order_id , Sort sort);

    // ☑️ final check
    @Query("""
       SELECT o.orderInformation
       FROM  Offer o
       WHERE o.specialist.id = :specialistId
       """)
    List<Order> findOrdersBySpecialistId(@Param("specialistId") Long specialistId);


}

/*
 */
/*  List<OfferOfSpecialistRequest> findAllByCustomerIdOrderBySuggestedPriceAsc(Long customerId)*/
