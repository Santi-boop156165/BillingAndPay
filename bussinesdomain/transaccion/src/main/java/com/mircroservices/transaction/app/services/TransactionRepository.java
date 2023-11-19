package com.mircroservices.transaction.app.services;

import com.mircroservices.transaction.app.model.entities.TransactionEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    @Query("SELECT t FROM TransactionEntity t WHERE t.clienteId = ?1")
    public List<TransactionEntity> findByClienteId(long clienteId);

}
