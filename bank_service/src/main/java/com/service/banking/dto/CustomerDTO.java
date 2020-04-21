package com.service.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDTO {
	private int custId;
	private String custName;
	private String gender;
	private Integer age;
	private String location;

}
