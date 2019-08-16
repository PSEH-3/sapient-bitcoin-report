package com.publicis.sapient.bitcoin.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.publicis.sapient.bitcoin.model.Bitcoin;

public interface BitcoinService {

	List<Bitcoin> getBitcoinDetails(LocalDate startDate, LocalDate endDate, String currency) throws IOException;
}
