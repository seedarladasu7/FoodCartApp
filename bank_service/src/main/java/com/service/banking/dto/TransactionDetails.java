package com.service.banking.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetails {
	private String fromAccount;
	private String toAccount;	
	private float txnAmount;
	private Timestamp txnDate;
	private String txnType;
}
