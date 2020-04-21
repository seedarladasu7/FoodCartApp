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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "txn_id")
	private Integer txnId;

	@Column(name = "from_account")
	private String fromAccount;

	@Column(name = "to_account")
	private String toAccount;

	@Column(name = "txn_amount")
	private float txnAmount;
	
	@Column(name = "txn_date")
	private Timestamp txnDate;
	
	@Column(name = "txn_type")
	private String txnType;
	
	@Column(name = "txn_mode")
	private String txnMode;

	@ManyToOne
	@JoinColumn(name = "acc_id")
	@JsonBackReference
	private Account account;

}
