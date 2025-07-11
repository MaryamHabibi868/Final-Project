package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends
        BaseRepository<Wallet, Long> {
}
