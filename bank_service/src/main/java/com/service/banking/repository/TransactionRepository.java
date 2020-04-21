package com.service.banking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.banking.entity.Account;
import com.service.banking.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	
	Optional<List<Transaction>> findByAccount(Account account);
	
	Optional<List<Transaction>> findByAccountAndTxnMode(Account account, String txnMode);

}
