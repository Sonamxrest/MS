package com.example.demo;

import lombok.Data;

@Data
public class Transaction {
    private String transactionId;
    private String paymentBank;
    private String recipientBank;
    private String transactionAmount;
    private String transactionDate;
    private String transactionTime;
    private String remarks;
}
