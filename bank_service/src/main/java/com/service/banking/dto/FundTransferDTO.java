package com.service.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundTransferDTO {

	private String fromAccount;
	private String toAccount;
	private float txnAmount;
	private String txnMode;
}
