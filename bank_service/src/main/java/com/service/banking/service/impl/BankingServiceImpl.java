package com.service.banking.service.impl;

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
import com.service.banking.dto.AccountDTO;
import com.service.banking.dto.AccountReqDTO;
import com.service.banking.dto.CustomerDTO;
import com.service.banking.dto.FundTransferDTO;
import com.service.banking.dto.TransactionDetails;
import com.service.banking.entity.Account;
import com.service.banking.entity.Customer;
import com.service.banking.entity.Transaction;
import com.service.banking.exceptionclasses.InvalidInputException;
import com.service.banking.exceptionclasses.RecordInsertionException;
import com.service.banking.exceptionclasses.RecordNotFoundException;
import com.service.banking.repository.AccountRepository;
import com.service.banking.repository.CustomerRepository;
import com.service.banking.repository.TransactionRepository;
import com.service.banking.service.BankingService;
import com.service.banking.util.BankingServiceUtils;

@Service
public class BankingServiceImpl implements BankingService {

	SimpleDateFormat simpDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	CustomerRepository custRepository;

	@Autowired
	AccountRepository accRepository;

	@Autowired
	TransactionRepository txnRepository;

	@Override
	public String registerCustomer(CustomerDTO custDTO) {
		Customer customer = new Customer();
		if (custDTO.getCustId() > 0) {
			customer.setCustId(custDTO.getCustId());
		}
		customer.setAge(custDTO.getAge());
		customer.setCustName(custDTO.getCustName());
		customer.setGender(custDTO.getGender());
		customer.setLocation(custDTO.getLocation());
		custRepository.save(customer);
		return "Customer Registered Successfully...";
	}

	@Override
	public String createAccountForCustomer(AccountReqDTO accDTO) {
		Optional<Customer> custOpt = custRepository.findById(accDTO.getCustId());
		if (custOpt.isPresent()) {
			Account account = getAccountInfofromReq(accDTO, custOpt.get());
			accRepository.save(account);
			return "Account has been created successfully...";
		}
		throw new InvalidInputException("Invalid Customer details provided...");
	}

	private Account getAccountInfofromReq(AccountReqDTO accDTO, Customer cust) {
		Account account = new Account();
		account.setBankName(accDTO.getBankName());
		account.setAccNumber(BankingServiceUtils.generateRandom(12));
		account.setCardNumber(BankingServiceUtils.generateRandom(16));
		account.setCvv(BankingServiceUtils.generateRandom(3));
		account.setExpiryDate(BankingServiceUtils.getCardExpiryDate());
		account.setAccOpeningOn(java.sql.Timestamp.valueOf(simpDate.format(new Date())));
		account.setMobileNumber(accDTO.getMobileNumber());
		account.setBalance(accDTO.getBalance());
		account.setStatus("open");
		account.setCustomer(cust);
		return account;
	}

	@Override
	public AccountDTO findAccountByAccountNumber(String acctNumber) {
		Account acctOptnl = accRepository.findByAccNumber(acctNumber);
		if (acctOptnl != null) {
			objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			return objectMapper.convertValue(acctOptnl, AccountDTO.class);
		}
		throw new InvalidInputException("Invalid Account details provided...");
	}

	@Override
	public List<AccountDTO> findAccountByMobileNumber(String mobileNum) {
		Optional<List<Account>> acctsListOptnl = accRepository.findByMobileNumber(mobileNum);

		if (acctsListOptnl.isPresent()) {
			List<Account> acctList = acctsListOptnl.get();
			if (!acctList.isEmpty()) {
				objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				return acctList.stream().map(acct -> objectMapper.convertValue(acct, AccountDTO.class))
						.collect(Collectors.toList());
			}
		}
		return new ArrayList<>();
	}

	@Override
	public String transferFunds(FundTransferDTO ftDTO) {

		String message = "";
		Account fromAcc = null;
		Account toAcc = null;

		try {
			switch (ftDTO.getPayMode()) {
			case ("card"):
				fromAcc = accRepository.findByCardNumberAndCvvAndExpiryDate(ftDTO.getFromAccCardNo(),
						ftDTO.getFromAccCvv(), ftDTO.getFromAccCardExpiryDate());
				break;

			case ("accNo"):
				fromAcc = accRepository.findByAccNumber(ftDTO.getFromAccount());
				break;

			default:
				fromAcc = accRepository.findByAccNumber(ftDTO.getFromAccount());

			}

			toAcc = accRepository.findByAccNumber(ftDTO.getToAccount());

			if (fromAcc == null || toAcc == null) {
				throw new InvalidInputException("Invalid Bank details provided...");
			}

			if (StringUtils.isEmpty(ftDTO.getTxnMode())) {
				ftDTO.setTxnMode("web");
			}

			if(StringUtils.isEmpty(ftDTO.getFromAccount()) || StringUtils.equals(ftDTO.getFromAccount() , "card")) {
				ftDTO.setFromAccount(fromAcc.getAccNumber());
			}
			
			if (fromAcc.getBalance() < ftDTO.getTxnAmount()) {
				throw new InvalidInputException("Insufficient balance...");
			} else {
				updateAndSaveTransaction(fromAcc, ftDTO, "fromAcc");
			}

			updateAndSaveTransaction(toAcc, ftDTO, "toAcc");
			
			message += "Transaction has been done successfully...";

		} catch (Exception ex) {
			throw new InvalidInputException(ex.getMessage());
		}
		return message;
	}

	private void updateAndSaveTransaction(Account acc, FundTransferDTO ftDTO, String accType) {
		float balance = 0f;
		String txnType = "";

		if (StringUtils.equals(accType, "fromAcc")) {
			balance = acc.getBalance() - ftDTO.getTxnAmount();
			txnType = "Debit";
		} else if (StringUtils.equals(accType, "toAcc")) {
			balance = acc.getBalance() + ftDTO.getTxnAmount();
			txnType = "Credit";
		}

		acc.setBalance(balance);
		accRepository.save(acc);

		Transaction txn = new Transaction();
		txn.setAccount(acc);
		txn.setTxnType(txnType);
		txn.setTxnDate(java.sql.Timestamp.valueOf(simpDate.format(new Date())));
		txn.setFromAccount(ftDTO.getFromAccount());
		txn.setToAccount(ftDTO.getToAccount());
		txn.setTxnAmount(ftDTO.getTxnAmount());
		txn.setTxnMode(ftDTO.getTxnMode());
		txnRepository.save(txn);

	}

	@Override
	public List<List<TransactionDetails>> retrieveCustomerBankStatement(int custId, String txnMode) {

		Optional<Customer> custOptnl = custRepository.findById(custId);

		if (custOptnl.isPresent()) {
			Customer customer = custOptnl.get();
			Optional<List<Account>> accOptnl = accRepository.findByCustomer(customer);

			if (accOptnl.isPresent()) {
				List<Account> acctList = accOptnl.get();
				List<List<TransactionDetails>> lists = new ArrayList<>();
				acctList.forEach(account -> {
					Optional<List<Transaction>> txnListOptnl = null;

					if (StringUtils.isNotEmpty(txnMode) && StringUtils.isNotEmpty(txnMode.trim())) {
						txnListOptnl = txnRepository.findByAccountAndTxnMode(account, txnMode);
					} else {
						txnListOptnl = txnRepository.findByAccount(account);
					}

					if (txnListOptnl.isPresent()) {
						List<Transaction> txnList = txnListOptnl.get();
						objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
						lists.add(txnList.stream().map(txn -> objectMapper.convertValue(txn, TransactionDetails.class))
								.collect(Collectors.toList()));
					}
				});
				return lists;
			} else {
				throw new RecordNotFoundException("Invalid Account for customer: " + customer.getCustName());
			}

		} else {
			throw new InvalidInputException("Invalid customer with customer id: " + custId);
		}
	}

}
