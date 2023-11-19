package com.mircroservices.transaction.app.model.dao;

import com.mircroservices.transaction.app.model.entities.TransactionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionDao extends CrudRepository<TransactionEntity , Long> {
    @Query("SELECT t FROM TransactionEntity t WHERE t.clienteId = ?1")
    public List<TransactionEntity> findByClienteId(long clienteId);
}
