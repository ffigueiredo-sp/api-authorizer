package com.caju.authorizer.infrastructure.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.caju.authorizer.infrastructure.entity.Amount;

public interface AmountRepository extends CrudRepository<Amount, Integer>{
	
	Amount findAllByAccountId(int accountId);
	
	List<Amount> findAllAmoutByAccountId(int accountId);
	
}


