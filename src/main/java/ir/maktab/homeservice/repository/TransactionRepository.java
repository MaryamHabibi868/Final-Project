package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.Transaction;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends
        BaseRepository<Transaction, Long> {

    Page<Transaction> findAllByWalletId(Long walletId, Pageable pageable);
}
