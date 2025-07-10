package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.OfferOfSpecialist;
import ir.maktab.homeservice.dto.OfferOfSpecialistRequest;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferOfSpecialistRepository
        extends BaseRepository<OfferOfSpecialist, Long> {}

/*
  */
/*  List<OfferOfSpecialistRequest> findAllByCustomerIdOrderBySuggestedPriceAsc(Long customerId)*/
