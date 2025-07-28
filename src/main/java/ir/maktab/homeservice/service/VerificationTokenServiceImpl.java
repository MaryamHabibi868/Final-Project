package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.VerificationToken;
import ir.maktab.homeservice.repository.VerificationTokenRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class VerificationTokenServiceImpl
        extends BaseServiceImpl<VerificationToken,
        Long, VerificationTokenRepository>
        implements VerificationTokenService {

    public VerificationTokenServiceImpl(
            VerificationTokenRepository repository) {
        super(repository);
    }


    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return repository.findByToken(token);
    }
}
