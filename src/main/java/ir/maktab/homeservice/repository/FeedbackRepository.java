package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.FeedBack;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository
        extends BaseRepository<FeedBack, Long> {
}
