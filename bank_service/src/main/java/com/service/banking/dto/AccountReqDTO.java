package com.service.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountReqDTO {

	private int custId;
	private String bankName;
	private String mobileNumber;
	private float balance;

}
