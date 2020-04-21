package com.service.food.service;

import java.util.List;

import com.service.food.dto.FoodItemDTO;
import com.service.food.dto.OrderFoodItemsDTO;
import com.service.food.dto.UserDTO;

public interface FoodCartService {

	public String registerUser(UserDTO userDTO);

	public List<UserDTO> findUserByMobileNumber(String mobileNum);

	public List<FoodItemDTO> findFoodItems(Integer vendorId, String itemName, String searchType);

	public String orderFoodItems(OrderFoodItemsDTO orderItemsDTO);

}
