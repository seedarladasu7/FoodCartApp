package com.service.banking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "customer")
@Data
@ToString
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cust_id")
	private Integer custId;

	@Column(name = "cust_name")
	private String custName;

	@Column(name = "gender")
	private String gender;

	@Column(name = "age")
	private Integer age;

	@Column(name = "location")
	private String location;
}