package com.publicis.sapient.bitcoin.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverterUtils {

	public static LocalDate convertStringToLocalDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(date, formatter);

	}
}
