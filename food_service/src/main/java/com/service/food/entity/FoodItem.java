package com.service.food.entity;

import java.util.Set;

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
@Table(name = "fooditem")
@Data
@ToString
public class FoodItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Integer itemId;

	@Column(name = "item_name")
	private String itemName;

	@Column(name = "item_price")
	private float itemPrice;

	@Column(name = "date")
	private String date;

	@ManyToOne
	@JoinColumn(name = "vendor_id")
	@JsonBackReference
	private Set<Vendor> vendor;

}
