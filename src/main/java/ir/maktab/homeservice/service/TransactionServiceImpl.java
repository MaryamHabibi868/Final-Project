package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Transaction;
import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.dto.TransactionResponse;
import ir.maktab.homeservice.mapper.TransactionMapper;
import ir.maktab.homeservice.repository.TransactionRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl extends
        BaseServiceImpl<Transaction, Long, TransactionRepository>
        implements TransactionService {

    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(TransactionRepository repository,
                                  TransactionMapper transactionMapper) {
        super(repository);
        this.transactionMapper = transactionMapper;
    }

    // ☑️ final check
    @Override
    public List<Transaction> findAllByWalletId(Long walletId) {
        return repository.findAllByWalletId(walletId);
    }

    /*//✅
    @Override
    public List<TransactionResponse> findAllForSpecialist(
            Long walletId) {
        return repository.findAllByWalletId(walletId)
                .stream().map(transactionMapper :: entityMapToResponse)
                .toList();
    }*/
}
