package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.domains.enumClasses.OfferStatus;
import ir.maktab.homeservice.repository.base.BaseUserRepository;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SpecialistRepository extends BaseUserRepository<Specialist> {

    List<Specialist> findAllByHomeServices_id(Long homeServiceId);


    Boolean existsByOffersStatusAndId(OfferStatus status, Long specialistId);


    @Query("select s.homeServices from Specialist s where s.id = :id")
    Set<HomeService> findHomeServicesBySpecialistId(@Param("id") Long id);


    List<Specialist> findAllByScoreIsBetween(Double lower, Double higher);


    List<Specialist> findAllByScoreIsLessThan(Double lower);
}
