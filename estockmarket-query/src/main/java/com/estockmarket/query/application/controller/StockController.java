package com.estockmarket.query.application.controller;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estockmarket.query.application.dto.StockAggregateDTO;
import com.estockmarket.query.application.dto.StockDto;
import com.estockmarket.query.domain.service.StockService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/${api.version}/query/market/stock")
@Api(value = "stocks", description = "Operations pertaining to fetch company stock price")
public class StockController {
	
	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StockService stockService;

	@ApiOperation(value = "Fetch company stocks based on given time frame", response = List.class)
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<StockDto>> getCompanyStocks(@RequestParam(value = "companyCode") String companyCode,
			@RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate) throws ParseException {
		LOGGER.info("fetch stocks based on code,date {}", companyCode,startDate,endDate);
		return new ResponseEntity<List<StockDto>>(stockService.getCompanyStocks(companyCode, startDate, endDate),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Get min,max and avg stock price", response = List.class)
	@RequestMapping(value = "/aggregate", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<StockAggregateDTO>> getCompanyAggregate(
			@RequestParam(value = "companyCode") String companyCode,
			@RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate) throws ParseException {
		LOGGER.info("fetch min,max and average stock price based on company code,date {}", companyCode,startDate,endDate);
		return new ResponseEntity<List<StockAggregateDTO>>(
				stockService.getStocksAggregate(companyCode, startDate, endDate), HttpStatus.OK);
	}

}
