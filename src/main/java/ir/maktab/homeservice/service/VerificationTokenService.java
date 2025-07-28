package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.VerificationToken;
import ir.maktab.homeservice.service.base.BaseService;
import java.util.Optional;

public interface VerificationTokenService
extends BaseService<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);
}
