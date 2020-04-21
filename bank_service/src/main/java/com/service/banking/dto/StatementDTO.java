package com.service.banking.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementDTO {
	private int custId;	
	private List<TransactionDetails> txnDetails;
}
