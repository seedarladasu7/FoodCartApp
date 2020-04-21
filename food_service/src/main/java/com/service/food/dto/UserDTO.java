package com.service.food.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
	private int userId;
	private String userName;
	private String gender;
	private Integer age;
	private String location;
	private String mobileNumber;
}
