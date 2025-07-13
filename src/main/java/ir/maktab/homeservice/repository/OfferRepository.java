package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.Offer;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository
        extends BaseRepository<Offer, Long> {

    @Query
    List<Offer> findAllBySpecialistId(Long specialist_id);


    List<Offer> findAllByOrder_Id(Long order_id);


Boolean existsByStatus_AcceptedAndSpecialistIdEquals(Long specialist_id);

Boolean existsByStatus_PendingAndSpecialistIdEquals(Long specialist_id);
}

/*
  */
/*  List<OfferOfSpecialistRequest> findAllByCustomerIdOrderBySuggestedPriceAsc(Long customerId)*/
