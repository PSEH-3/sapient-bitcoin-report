package com.publicis.sapient.bitcoin.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.publicis.sapient.bitcoin.model.Bitcoin;
import com.publicis.sapient.bitcoin.service.BitcoinService;
import com.publicis.sapient.bitcoin.utils.DateConverterUtils;

@Service
public class BitcoinServiceImpl implements BitcoinService {

	private static final String BITCOIN_URL = "https://api.coindesk.com/v1/bpi/historical/close.json";

	@Override
	public List<Bitcoin> getBitcoinDetails(LocalDate startDate, LocalDate endDate, String currency) throws IOException {

		RestTemplate restTemplate = new RestTemplate();
		List<Bitcoin> bitcoinList =  new ArrayList<Bitcoin>();

		// Fetch data from Bitcoin API
		HttpEntity<?> entity = new HttpEntity<>(getHeaders());
		HttpEntity<String> response = restTemplate.exchange(getUri(startDate, endDate, currency), HttpMethod.GET,
				entity, String.class);

		System.out.println("Response: " + response.getBody());

		if (response.hasBody()) {

			ObjectMapper mapper = new ObjectMapper();
			try {

				JsonNode rootNode = mapper.readTree(response.getBody());
				String responseString = rootNode.get("bpi").toString().replace("{", "").replace("}", "").replaceAll("\"",
						"");
				Optional.ofNullable(responseString).ifPresent(resStr ->{

					Map<String, Double> bitcoinMap = new HashMap<String, Double>();

					for (String data : resStr.split(",")) {
						Optional.ofNullable(data.split(":")).ifPresent(priceArr ->bitcoinMap.put(priceArr[0], Double.valueOf(priceArr[1])));
					}

					String maxValueDate = Collections.max(bitcoinMap.entrySet(), Map.Entry.comparingByValue()).getKey();

					for (Map.Entry<String, Double> entry : bitcoinMap.entrySet()) {
						Bitcoin bitcoin = new Bitcoin();
						
						if (entry.getKey().equals(maxValueDate))
							bitcoin.setPrice(entry.getValue() + " (highest)");
						else
							bitcoin.setPrice(entry.getValue().toString());

						bitcoin.setDate(DateConverterUtils.convertStringToLocalDate(entry.getKey()));

						bitcoin.setCurrency(currency);

						bitcoinList.add(bitcoin);
					}
				});
				
			} catch (IOException ex) {
				throw ex;
			}
		}

		return bitcoinList;
	}

	private String getUri(LocalDate startDate, LocalDate endDate, String currency) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BITCOIN_URL).queryParam("currency", currency)
				.queryParam("start", startDate).queryParam("end", endDate);

		System.out.println("URI: " + builder.toUriString());
		return builder.toUriString();

	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		return headers;
	}

}
