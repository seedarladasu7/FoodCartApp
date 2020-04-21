package com.service.food.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.food.client.BankServiceClient;
import com.service.food.dto.FoodItemDTO;
import com.service.food.dto.FundTransferDTO;
import com.service.food.dto.OrderFoodItemsDTO;
import com.service.food.dto.TransactionDetails;
import com.service.food.dto.UserDTO;
import com.service.food.service.FoodCartService;

@RestController
@RequestMapping("/foodcart")
public class FoodCartServiceController {

	private static ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	BankServiceClient bankServiceClient;

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

	@GetMapping("/user/orderFoor")
	public ResponseEntity<String> orderFood(@RequestBody OrderFoodItemsDTO orderItemsDTO) {

		String message = foodService.orderFoodItems(orderItemsDTO);

		if (StringUtils.isEmpty(message)) {
			FundTransferDTO fndTx = objectMapper.convertValue(orderItemsDTO, FundTransferDTO.class);

			String txnStatus = bankServiceClient.transferFunds(fndTx);

			if (StringUtils.containsIgnoreCase(txnStatus, "success")) {
				return new ResponseEntity<>("Order has been placed successfully..", HttpStatus.OK);
			}
		}

		return new ResponseEntity<>("Order has been failed", HttpStatus.OK);

	}

	@PostMapping("/fundTransfer")
	public ResponseEntity<String> transferFunds(@RequestBody FundTransferDTO ftDTO) {
		return new ResponseEntity<>(bankServiceClient.transferFunds(ftDTO), HttpStatus.OK);
	}

	@GetMapping("/{custId}/{txnMode}/statement")
	public ResponseEntity<List<List<TransactionDetails>>> getStatement(@PathVariable("custId") Integer custId,
			@PathVariable("txnMode") String txnMode) {
		return new ResponseEntity<>(bankServiceClient.getStatement(custId, txnMode), HttpStatus.ACCEPTED);
	}

}
