package com.estockmarket.command.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estockmarket.command.application.dto.StockDto;
import com.estockmarket.command.domain.service.StockService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/${api.version}/command/market/stock")
@Api(value = "stock", description = "Operations pertaining to add,update and delete stock price for the company")
public class StockController {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StockService stockService;

	@ApiOperation(value = "Create stocks", response = StockDto.class)
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<StockDto> createStock(@RequestBody StockDto stockDto) throws JsonProcessingException {
		LOGGER.info("Save stock details {}", stockDto);
		return new ResponseEntity<StockDto>(stockService.createStock(stockDto), HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Update stocks", response = StockDto.class)
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<StockDto> updateStock(@RequestBody StockDto stockDto) throws JsonProcessingException {
		LOGGER.info("Update stock details {}", stockDto);
		return new ResponseEntity<StockDto>(stockService.updateStock(stockDto), HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Delete company stocks", response = Boolean.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Boolean> deleteCompanyStocks(@PathVariable Long id) throws JsonProcessingException {
		LOGGER.info("Delete company stocks through{}", id);
		return stockService.deleteCompanyStocks(id);
	}


}
