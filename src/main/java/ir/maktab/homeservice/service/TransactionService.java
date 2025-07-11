package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Transaction;
import ir.maktab.homeservice.dto.TransactionResponse;
import ir.maktab.homeservice.service.base.BaseService;

import java.util.List;

public interface TransactionService
extends BaseService<Transaction, Long> {

    //✅
    List<TransactionResponse> findAllForSpecialist(
            Long walletId);

}
