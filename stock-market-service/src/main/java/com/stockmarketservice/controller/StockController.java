package com.stockmarketservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stockmarketservice.dto.StockAggregateDTO;
import com.stockmarketservice.dto.StockDto;
import com.stockmarketservice.service.StockService;

@RequestMapping("/market/stock")
public class StockController {

	@Autowired
	private StockService stockService;

	@PostMapping("/")
	public ResponseEntity<StockDto> createCompany(@RequestBody StockDto stockDto) {
		return new ResponseEntity<StockDto>( stockService.createStock(stockDto),HttpStatus.CREATED);
	}

	@GetMapping("/{companyCode}/{startDate}/{endDate}")
	public ResponseEntity<List<StockDto>> getCompany(@PathVariable String companyCode, @PathVariable String startDate,
			@PathVariable String endDate) {
		return new ResponseEntity<List<StockDto>>(stockService.getStocks(companyCode, startDate, endDate),HttpStatus.OK);
	}

	@DeleteMapping("/{companyCode}")
	public ResponseEntity<Boolean> deleteCompany(@PathVariable String companyCode) {
		return stockService.deleteStocks(companyCode);
	}
	
	@GetMapping("/aggregate/{companyCode}/{startDate}/{endDate}")
	public ResponseEntity<List<StockAggregateDTO>> getCompanyAggregate(@PathVariable String companyCode, @PathVariable String startDate,
			@PathVariable String endDate) {
		return new ResponseEntity<List<StockAggregateDTO>>(stockService.getStocksAggregate(companyCode, startDate, endDate),HttpStatus.OK);
	}

}
