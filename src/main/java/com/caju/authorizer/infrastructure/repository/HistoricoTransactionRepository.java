package com.caju.authorizer.infrastructure.repository;

import com.caju.authorizer.infrastructure.entity.HistoricoTransaction;
import org.springframework.data.repository.CrudRepository;

public interface HistoricoTransactionRepository extends CrudRepository<HistoricoTransaction, Integer> {
}

