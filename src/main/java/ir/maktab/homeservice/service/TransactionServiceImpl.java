package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Transaction;
import ir.maktab.homeservice.repository.TransactionRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl extends
        BaseServiceImpl<Transaction, Long, TransactionRepository>
        implements TransactionService {


    public TransactionServiceImpl(TransactionRepository repository) {
        super(repository);
    }


    @Override
    public Page<Transaction> findAllByWalletId(Long walletId, Pageable pageable) {
        return repository.findAllByWalletId(walletId, pageable);
    }
}
