package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.VerificationToken;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository
extends BaseRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);
}
