package com.service.food.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vendor_id")
	private Integer verdorId;
	
	@Column(name = "vendor_name")
	private String vendorName;

}
