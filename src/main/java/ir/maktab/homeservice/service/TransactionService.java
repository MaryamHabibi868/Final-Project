package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Transaction;
import ir.maktab.homeservice.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService
extends BaseService<Transaction, Long> {


    Page<Transaction> findAllByWalletId(Long walletId, Pageable pageable);
}
