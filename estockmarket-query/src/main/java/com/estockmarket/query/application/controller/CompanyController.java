package com.estockmarket.query.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estockmarket.query.application.dto.CompanyDto;
import com.estockmarket.query.application.dto.CompanyStockDto;
import com.estockmarket.query.domain.service.CompanyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/${api.version}/query/market/company")
@Api(value = "company", description = "Operations fetch the company details")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@ApiOperation(value = "Fetch companies", response = List.class)
	@RequestMapping(value = "/getall", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<CompanyDto>> getCompany() {
		return new ResponseEntity<List<CompanyDto>>(companyService.getCompany(), HttpStatus.OK);
	}

	@ApiOperation(value = "Fetch company through code", response = CompanyDto.class)
	@RequestMapping(value = "/info/{code}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<CompanyDto> getCompanyId(@PathVariable String code) {
		return new ResponseEntity<CompanyDto>(companyService.getCompanyById(code), HttpStatus.OK);
	}

	@ApiOperation(value = "View company latest stockprice by code", response = CompanyStockDto.class)
	@RequestMapping(value = "/view/{code}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<CompanyStockDto> viewCompany(@PathVariable String code) {
		return new ResponseEntity<CompanyStockDto>(companyService.viewCompanyLatestPrice(code), HttpStatus.OK);
	}

}