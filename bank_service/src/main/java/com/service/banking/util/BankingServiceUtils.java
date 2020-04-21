package com.service.banking.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

public class BankingServiceUtils {

	static SimpleDateFormat mmyyFormat = new SimpleDateFormat("MM/yy");

	private BankingServiceUtils() {
	}

	static Random random;
	static {
		try {
			random = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/*public static void main(String[] args) {
		System.out.println(BankingServiceUtils.generateRandom(12));
		System.out.println(BankingServiceUtils.getCardExpiryDate());
	}*/

	public static String generateRandom(int length) {
		char[] digits = new char[length];
		digits[0] = (char) (random.nextInt(9) + '1');
		for (int i = 1; i < length; i++) {
			digits[i] = (char) (random.nextInt(10) + '0');
		}
		return new String(digits);
	}

	public static String getCardExpiryDate() {
		LocalDateTime localDateTime = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		return mmyyFormat.format(Date.from(localDateTime.plusYears(5).atZone(ZoneId.systemDefault()).toInstant()));
	}

	public static boolean isCardateExpired(String mmyy) {
		mmyyFormat.setLenient(false);
		boolean isExpired = true;

		Date expiry;
		try {
			expiry = mmyyFormat.parse(mmyy);
			return !expiry.before(new Date());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return isExpired;
	}

}
