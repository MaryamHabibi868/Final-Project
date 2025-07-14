package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Transaction;
import ir.maktab.homeservice.repository.TransactionRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionServiceImpl extends
        BaseServiceImpl<Transaction, Long, TransactionRepository>
        implements TransactionService {


    public TransactionServiceImpl(TransactionRepository repository) {
        super(repository);
    }


    @Override
    public List<Transaction> findAllByWalletId(Long walletId) {
        return repository.findAllByWalletId(walletId);
    }
}
