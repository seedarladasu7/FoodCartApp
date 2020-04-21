package com.service.food.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.service.food.entity.FoodItem;
import com.service.food.service.FoodCartService;

@Component
public class ValidationFoodItemProcessor implements ItemProcessor<FoodItem, FoodItem> {

	@Autowired
	FoodCartService cartService;

	public FoodItem process(FoodItem vendor) throws Exception {
		System.out.println("Processing Vendor: " + vendor);
		/*
		 * if (user.getId() == null) { System.out.println("Missing employee id : " +
		 * user.getId()); return null; }
		 * 
		 * try { if (Integer.valueOf(user.getId()) <= 0) {
		 * System.out.println("Invalid employee id : " + user.getId()); return null; } }
		 * catch (NumberFormatException e) { System.out.println("Invalid employee id : "
		 * + user.getId()); return null; }
		 */
		return vendor;
	}
}
