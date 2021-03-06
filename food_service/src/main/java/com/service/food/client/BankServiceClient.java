package com.service.food.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.service.food.dto.FundTransferDTO;
import com.service.food.dto.TransactionDetails;

@FeignClient(name = "http://bank-service/BankingService/customer")
public interface BankServiceClient {

	@GetMapping("/info")
	public String getPort();

	@PostMapping("/fundTransfer")
	public String transferFunds(@RequestBody FundTransferDTO ftDTO);

	@GetMapping("/{custId}/{txnMode}/statement")
	public List<List<TransactionDetails>> getStatement(@PathVariable("custId") Integer custId,
			@PathVariable("txnMode") String txnMode);

}
