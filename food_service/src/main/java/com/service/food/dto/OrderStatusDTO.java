package com.service.food.dto;

import com.service.food.entity.FoodOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDTO {

	private String orderStatus;
	private String errorStatus;
	private FoodOrder foodOrder;
	private float orderAmount;

}
