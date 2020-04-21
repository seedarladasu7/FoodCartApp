package com.service.banking.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "account")
@Data
@ToString
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private Integer accId;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "acc_number")
	private String accNumber;

	@Column(name = "card_number")
	private String cardNumber;

	@Column(name = "cvv")
	private String cvv;

	@Column(name = "expiry_date")
	private String expiryDate;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "acc_opening_on")
	private Timestamp accOpeningOn;

	@Column(name = "balance")
	private float balance;

	@Column(name = "status")
	private String status;

	@ManyToOne
	@JoinColumn(name = "cust_id")
	@JsonBackReference
	private Customer customer;

}
