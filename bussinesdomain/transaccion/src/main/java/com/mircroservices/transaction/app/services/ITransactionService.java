package com.mircroservices.transaction.app.services;

import com.mircroservices.transaction.app.model.entities.TransactionEntity;

public interface ITransactionService {
    TransactionEntity save(TransactionEntity transactionEntity);
    TransactionEntity findById(Long id);

    Iterable<TransactionEntity> findAll();

    void deleteById(Long id);

    Boolean existsById(Long id);

    public Iterable<TransactionEntity> findByClienteId(long clienteId);
}
