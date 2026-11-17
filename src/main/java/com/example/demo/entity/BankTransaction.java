package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bank_transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankTransaction {
    @Id
    private String transactionId;
    private String accountId;
    private String transactionType;   // CREDIT or DEBIT
    private Double amount;
    private String description;
    private LocalDateTime transactionDate;
}