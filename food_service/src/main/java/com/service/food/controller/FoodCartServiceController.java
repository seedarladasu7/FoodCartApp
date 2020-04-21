package com.service.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.food.dto.FoodItemDTO;
import com.service.food.dto.UserDTO;
import com.service.food.service.FoodCartService;

@RestController
@RequestMapping("/foodcart")
public class FoodCartServiceController {

	@Autowired
	FoodCartService foodService;

	@GetMapping("/appPort")
	public String getAppPort() {
		return "Hiii";
	}

	@PostMapping("/user/register")
	public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
		return new ResponseEntity<>(foodService.registerUser(userDTO), HttpStatus.ACCEPTED);
	}

	@GetMapping("/user/{mobileNum}")
	public ResponseEntity<List<UserDTO>> findAccountByMobileNumber(@PathVariable("mobileNum") String mobileNum) {
		return new ResponseEntity<>(foodService.findUserByMobileNumber(mobileNum), HttpStatus.OK);
	}

	@GetMapping("/items/byVendor/{vendorId}")
	public ResponseEntity<List<FoodItemDTO>> findFoodItemsByVendor(@PathVariable("vendorId") Integer vendorId) {
		return new ResponseEntity<>(foodService.findFoodItems(vendorId, null, "vendorId"), HttpStatus.OK);
	}

	@GetMapping("/items/byItemName/{itemName}")
	public ResponseEntity<List<FoodItemDTO>> findFoodItemsByItemName(@PathVariable("itemName") String itemName) {
		return new ResponseEntity<>(foodService.findFoodItems(0, itemName, "itemName"), HttpStatus.OK);
	}

}
