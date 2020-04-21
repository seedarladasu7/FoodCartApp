package com.service.food.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundTransferDTO {

	private String fromAccount;
	private String fromAccCardNo;
	private String fromAccCvv;
	private String fromAccCardExpiryDate;
	private String toAccount;
	private float txnAmount;
	private String txnMode;
	private String payMode;
}
