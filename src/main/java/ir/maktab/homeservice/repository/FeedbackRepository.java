package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.Feedback;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository
        extends BaseRepository<Feedback, Long> {

    @Query ("select f from Feedback f where f.offer.id = :offerId ")
    Feedback findByOfferId(@Param("offerId") Long offerId);

    // coalesce (x , defaultValue)
    // coalesce(avg(f.range) , 0)

    @Query ("select avg(f.range) from Feedback f " +
            "where f.offer.specialist.id = :specialistId")
    Double findAverageRangeBySpecialistId(@Param ("specialistId") Long specialistId);
}
