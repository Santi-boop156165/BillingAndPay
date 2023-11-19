package com.mircroservices.transaction.app.services.impl;

import com.mircroservices.transaction.app.model.dao.TransactionDao;
import com.mircroservices.transaction.app.model.entities.TransactionEntity;
import com.mircroservices.transaction.app.services.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements ITransactionService {
    @Autowired
    private TransactionDao  transactionDao;

    @Override
    public TransactionEntity save(TransactionEntity transactionEntity) {
        return transactionDao.save(transactionEntity);
    }

    @Override
    public TransactionEntity findById(Long id) {
        return transactionDao.findById(id).orElse(null);
    }

    @Override
    public Iterable<TransactionEntity> findAll() {
        return transactionDao.findAll();
    }

    @Override
    public void deleteById(Long id) {
        transactionDao.deleteById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return transactionDao.existsById(id);
    }

    @Override
    public Iterable<TransactionEntity> findByClienteId(long clienteId) {
        return transactionDao.findByClienteId(clienteId);
    }
}
