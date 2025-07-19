package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.Offer;
import ir.maktab.homeservice.domains.Order;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OfferRepository
        extends BaseRepository<Offer, Long> {


    Page<Offer> findAllBySpecialistId(Long specialist_id, Pageable pageable);




    List<Offer> findAllByOrderInformation_Id(Long order_id);


    Page<Offer> findAllByOrderInformation_Id(
            Long order_id, Pageable pageable);


    @Query("""
       SELECT o.orderInformation
       FROM  Offer o
       WHERE o.specialist.id = :specialistId
       """)
    Page<Order> findOrdersBySpecialistId(
            @Param("specialistId") Long specialistId, Pageable pageable);
}
