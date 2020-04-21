package com.service.food.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
	private String bankName;
	private String accNumber;
	private String cardNumber;
	private String cvv;
	private String expiryDate;
	private String mobileNumber;
	private Timestamp accOpeningOn;
	private float balance;
	private String status;
	private int custId;
}
