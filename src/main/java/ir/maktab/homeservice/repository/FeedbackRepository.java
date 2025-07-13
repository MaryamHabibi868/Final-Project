package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.Feedback;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository
        extends BaseRepository<Feedback, Long> {
}
