package com.service.food.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItemDTO {

	private String itemName;
	private float itemPrice;
	private String date;
	private Integer vendorId;

}
