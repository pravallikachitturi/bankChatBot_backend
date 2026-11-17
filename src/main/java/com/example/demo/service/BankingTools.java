package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.entity.BankTransaction;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.BankTransactionRepository;

import dev.langchain4j.agent.tool.Tool;

@Component
public class BankingTools {

    private final AccountRepository accountRepository;
    private final BankTransactionRepository transactionRepository;


    public BankingTools(AccountRepository accountRepository, BankTransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;

    }
    @Tool("Get the most recent transactions (credits and debits) for a customer by their name")
    public String getTransactionHistory(String customerName) {
        System.out.println(">>> TOOL CALLED: getTransactionHistory for " + customerName);

        return accountRepository.findByOwnerName(customerName)
            .map(acc -> {
                List<BankTransaction> txns = transactionRepository
                    .findTop5ByAccountIdOrderByTransactionDateDesc(acc.getAccountId());

                if (txns.isEmpty()) {
                    return "No transactions found for " + customerName;
                }

                StringBuilder sb = new StringBuilder("Recent transactions for " + customerName + ":\n");
                for (BankTransaction t : txns) {
                    sb.append(String.format("- %s | %s | ₹%.2f | %s | %s%n",
                        t.getTransactionId(), t.getTransactionType(), t.getAmount(),
                        t.getDescription(), t.getTransactionDate()));
                }
                return sb.toString();
            })
            .orElse("Customer not found: " + customerName);
    }

    @Tool("Get the account balance for a customer by their name")
    public String getAccountBalance(String customerName) {
        return accountRepository.findByOwnerName(customerName)
            .map(acc -> String.format(
                "Account ID: %s, Balance: ₹%.2f, Type: %s",
                acc.getAccountId(), acc.getBalance(), acc.getAccountType()))
            .orElse("No account found for " + customerName);
    }

    @Tool("Get all account details for a customer")
    public String getAccountDetails(String customerName) {
        return accountRepository.findByOwnerName(customerName)
            .map(acc -> String.format(
                "Owner: %s, Account: %s, Balance: ₹%.2f, Type: %s",
                acc.getOwnerName(), acc.getAccountId(),
                acc.getBalance(), acc.getAccountType()))
            .orElse("Customer not found: " + customerName);
    }
    
    @Tool("Calculate simple interest for a given principal, rate and time in years")
    public String calculateInterest(double principal, double ratePercent, int years) {
        double interest = (principal * ratePercent * years) / 100;
        double total = principal + interest;
        return String.format(
            "Principal: ₹%.2f, Interest earned: ₹%.2f, Total amount: ₹%.2f",
            principal, interest, total);
    }
}