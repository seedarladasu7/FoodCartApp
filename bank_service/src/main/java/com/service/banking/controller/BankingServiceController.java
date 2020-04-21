package com.service.banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.banking.dto.AccountDTO;
import com.service.banking.dto.AccountReqDTO;
import com.service.banking.dto.CustomerDTO;
import com.service.banking.dto.FundTransferDTO;
import com.service.banking.dto.TransactionDetails;
import com.service.banking.service.BankingService;

@RestController
@RequestMapping("/customer")
public class BankingServiceController {

	@Autowired
	Environment env;

	@Autowired
	BankingService bankingService;

	@GetMapping("/appPort")
	public String getAppPort() {
		String port = env.getProperty("local.server.port");
		return port;
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerCustomer(@RequestBody CustomerDTO userDTO) {
		return new ResponseEntity<>(bankingService.registerCustomer(userDTO), HttpStatus.ACCEPTED);
	}

	@PostMapping("/account")
	public ResponseEntity<String> createAccount(@RequestBody AccountReqDTO accDTO) {
		return new ResponseEntity<>(bankingService.createAccountForCustomer(accDTO), HttpStatus.ACCEPTED);
	}

	@GetMapping("/account/byAcctNum/{acctNum}")
	public ResponseEntity<AccountDTO> findAccountByAccNumber(@PathVariable("acctNum") String acctNum) {
		return new ResponseEntity<>(bankingService.findAccountByAccountNumber(acctNum), HttpStatus.OK);
	}

	@GetMapping("/account/byMobileNum/{mobileNum}")
	public ResponseEntity<List<AccountDTO>> findAccountByMobileNumber(@PathVariable("mobileNum") String mobileNum) {
		return new ResponseEntity<>(bankingService.findAccountByMobileNumber(mobileNum), HttpStatus.OK);
	}

	@PostMapping("/fundTransfer")
	public ResponseEntity<String> transferFunds(@RequestBody FundTransferDTO ftDTO) {
		return new ResponseEntity<>(bankingService.transferFunds(ftDTO), HttpStatus.OK);
	}

	@GetMapping("/{custId}/{txnMode}/statement")
	public ResponseEntity<List<List<TransactionDetails>>> getStatement(@PathVariable("custId") Integer custId,
			@PathVariable("txnMode") String txnMode) {
		return new ResponseEntity<>(bankingService.retrieveCustomerBankStatement(custId, txnMode), HttpStatus.ACCEPTED);
	}

}
