package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.FeedBack;
import ir.maktab.homeservice.repository.FeedbackRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl
        extends BaseServiceImpl<FeedBack, Long, FeedbackRepository>
        implements FeedbackService {
    public FeedbackServiceImpl(FeedbackRepository repository) {
        super(repository);
    }
}
