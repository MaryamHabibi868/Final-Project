package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.domains.enumClasses.OfferStatus;
import ir.maktab.homeservice.repository.base.BaseUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialistRepository extends BaseUserRepository<Specialist> {

    Page<Specialist> findAllByHomeServices_id(
            Long homeServiceId, Pageable pageable);


    Boolean existsByOffersStatusAndId(
            OfferStatus status, Long specialistId);


    @Query("select s.homeServices from Specialist s where s.id = :id")
    Page<HomeService> findHomeServicesBySpecialistId(
            @Param("id") Long id, Pageable pageable);


    Page<Specialist> findAllByScoreIsBetween(
            Double lower, Double higher, Pageable pageable);


    List<Specialist> findAllByScoreIsLessThan(Double lower);


    @Override
    @EntityGraph(attributePaths = {"offers"})
    Optional<Specialist> findById(Long offerId);
}
