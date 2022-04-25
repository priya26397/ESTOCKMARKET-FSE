package com.stockmarket.companystocks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.companystocks.dto.StockAggregateDTO;
import com.stockmarket.companystocks.dto.StockDto;
import com.stockmarket.companystocks.service.StockService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/${api.version}/market/stock")
@Api(value = "user", description = "Operations pertaining to add stock price for the company")
public class StockController {

	@Autowired
	private StockService stockService;

	@ApiOperation(value = "Create stocks", response = StockDto.class)
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<StockDto> createStock(@RequestBody StockDto stockDto) {
		return new ResponseEntity<StockDto>(stockService.createStock(stockDto), HttpStatus.CREATED);
	}

	@ApiOperation(value = "Fetch company stocks based on given time frame", response = List.class)
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<StockDto>> getCompanyStocks(@RequestParam(value="companyCode") String companyCode,
			@RequestParam(value="startDate") String startDate, @RequestParam(value="endDate") String endDate) {
		return new ResponseEntity<List<StockDto>>(stockService.getCompanyStocks(companyCode, startDate, endDate),
				HttpStatus.OK);
	}

	
	@ApiOperation(value = "Update stocks", response = StockDto.class)
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<StockDto> updateStock(@RequestBody StockDto stockDto) {
		return new ResponseEntity<StockDto>(stockService.updateStock(stockDto), HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Delete company stocks", response = Boolean.class)
	@RequestMapping(value = "/{companyCode}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Boolean> deleteCompanyStocks(@PathVariable String companyCode) {
		return stockService.deleteCompanyStocks(companyCode);
	}

	@ApiOperation(value = "Get min,max and avg stock price", response = List.class)
	@RequestMapping(value = "/aggregate", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<StockAggregateDTO>> getCompanyAggregate(@RequestParam(value="companyCode") String companyCode,
			@RequestParam(value="startDate") String startDate, @RequestParam(value="endDate") String endDate) {
		return new ResponseEntity<List<StockAggregateDTO>>(
				stockService.getStocksAggregate(companyCode, startDate, endDate), HttpStatus.OK);
	}

}
