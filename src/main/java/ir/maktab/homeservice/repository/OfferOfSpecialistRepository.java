package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.OfferOfSpecialist;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferOfSpecialistRepository
        extends BaseRepository<OfferOfSpecialist, Long> {

    @Query
    List<OfferOfSpecialist> findAllBySpecialistId(Long specialist_id);
}

/*
  */
/*  List<OfferOfSpecialistRequest> findAllByCustomerIdOrderBySuggestedPriceAsc(Long customerId)*/
