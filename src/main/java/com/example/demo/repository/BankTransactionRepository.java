package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BankTransaction;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, String> {
    List<BankTransaction> findTop5ByAccountIdOrderByTransactionDateDesc(String accountId);
}