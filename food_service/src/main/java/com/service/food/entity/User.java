package com.service.food.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "user")
@Data
@ToString
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "gender")
	private String gender;

	@Column(name = "age")
	private Integer age;

	@Column(name = "location")
	private String location;

	@Column(name = "mobile_number")
	private String mobileNumber;
}