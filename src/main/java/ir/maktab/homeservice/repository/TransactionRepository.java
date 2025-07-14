package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.Transaction;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends
        BaseRepository<Transaction, Long> {

    List<Transaction> findAllByWalletId(Long walletId);
}
