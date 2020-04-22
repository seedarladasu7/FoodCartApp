package com.service.food.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.food.dto.FoodItemDTO;
import com.service.food.dto.OrderFoodItemsDTO;
import com.service.food.dto.UserDTO;
import com.service.food.entity.FoodItem;
import com.service.food.entity.FoodOrder;
import com.service.food.entity.OrderItem;
import com.service.food.entity.User;
import com.service.food.exceptionclasses.InvalidInputException;
import com.service.food.exceptionclasses.RecordNotFoundException;
import com.service.food.repository.FoodItemRepository;
import com.service.food.repository.FoodOrderRepository;
import com.service.food.repository.OrderItemRepository;
import com.service.food.repository.UserRepository;
import com.service.food.service.FoodCartService;

@Service
public class FoodCartServiceImpl implements FoodCartService {

	private static ObjectMapper objectMapper = new ObjectMapper();

	SimpleDateFormat simpDate = new SimpleDateFormat("dd-MM-yyyy");

	@Autowired
	UserRepository userRepository;

	@Autowired
	FoodOrderRepository foodOrderRepository;

	@Autowired
	FoodItemRepository foodItemRepository;

	@Autowired
	OrderItemRepository orderItemRepository;

	@Override
	public String registerUser(UserDTO userDTO) {
		User user = new User();
		if (userDTO.getUserId() > 0) {
			user.setUserId(userDTO.getUserId());
		}
		user.setAge(userDTO.getAge());
		user.setUserName(userDTO.getUserName());
		user.setGender(userDTO.getGender());
		user.setMobileNumber(userDTO.getMobileNumber());
		user.setLocation(userDTO.getLocation());
		userRepository.save(user);
		return "User has been Registered Successfully...";
	}

	@Override
	public List<UserDTO> findUserByMobileNumber(String mobileNum) {
		Optional<List<User>> acctsListOptnl = userRepository.findByMobileNumber(mobileNum);

		if (acctsListOptnl.isPresent()) {
			List<User> acctList = acctsListOptnl.get();
			if (!acctList.isEmpty()) {
				objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				return acctList.stream().map(user -> objectMapper.convertValue(user, UserDTO.class))
						.collect(Collectors.toList());
			}
		}
		return new ArrayList<>();
	}

	@Override
	public List<FoodItemDTO> findFoodItems(Integer vendorId, String itemName, String searchType) {

		Optional<List<FoodItem>> foodItemListOpt = null;

		String date = simpDate.format(new Date());

		if (StringUtils.equalsIgnoreCase(searchType, "vendorId")) {
			foodItemListOpt = foodItemRepository.findByVendorIdAndDate(vendorId, date);
		} else if (StringUtils.equalsIgnoreCase(searchType, "itemName")) {
			foodItemListOpt = foodItemRepository.findAllByItemName(itemName, date);
		}

		if (foodItemListOpt.isPresent()) {
			List<FoodItem> foodItemList = foodItemListOpt.get();
			if (!foodItemList.isEmpty()) {
				objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				return foodItemList.stream().map(foodItem -> objectMapper.convertValue(foodItem, FoodItemDTO.class))
						.collect(Collectors.toList());
			}
		}
		throw new RecordNotFoundException("No Food items found...");
	}

	@Override
	public String orderFoodItems(OrderFoodItemsDTO orderItemsDTO) {

		if (StringUtils.isNotEmpty(String.valueOf(orderItemsDTO.getUserId()))) {
			Optional<User> userOpt = userRepository.findById(orderItemsDTO.getUserId());

			if (userOpt.isPresent()) {
				User user = userOpt.get();
				FoodOrder foodOrder = new FoodOrder();
				foodOrder.setUser(user);
				foodOrder.setOrderDate(simpDate.format(new Date()));
				foodOrder = foodOrderRepository.save(foodOrder);

				OrderItem orderItem = null;
				Optional<FoodItem> foodItemOpt = null;

				for (Integer itemId : orderItemsDTO.getFoodItems()) {
					foodItemOpt = foodItemRepository.findById(itemId);
					if (foodItemOpt.isPresent()) {
						orderItem = new OrderItem();
						orderItem.setFoodOrder(foodOrder);
						orderItem.setItemId(itemId);
						orderItemRepository.save(orderItem);
					} else {
						throw new RecordNotFoundException("foodItem is not available with ID: " + itemId.toString());
					}
				}

			} else {
				throw new InvalidInputException("Invalid user...");
			}
		} else {
			throw new InvalidInputException("Invalid user...");
		}
		return StringUtils.EMPTY;
	}

}
