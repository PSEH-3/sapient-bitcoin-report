package com.publicis.sapient.bitcoin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.publicis.sapient.bitcoin.model.Bitcoin;
import com.publicis.sapient.bitcoin.service.BitcoinService;
import com.publicis.sapient.bitcoin.utils.DateConverterUtils;

@RestController
public class BitcoinController {

	@Autowired
	BitcoinService bitcoinService;

	@GetMapping(value = "/{currency}/{startDate}/{endDate}", produces = "application/json")
	public ResponseEntity<List<Bitcoin>> getBitcoinReport(@PathVariable("currency") String currency,
			@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {
		List<Bitcoin> bitcoinList = null;
		try {
			bitcoinList = bitcoinService.getBitcoinDetails(DateConverterUtils.convertStringToLocalDate(startDate),
					DateConverterUtils.convertStringToLocalDate(endDate), currency);
		} catch (Exception e) {
			return new ResponseEntity("Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(bitcoinList, HttpStatus.OK);

	}
}
