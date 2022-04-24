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
import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.companystocks.dto.CompanyDto;
import com.stockmarket.companystocks.dto.CompanyStockDto;
import com.stockmarket.companystocks.service.CompanyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/${api.version}/market/company")
@Api(value = "user", description = "Operations pertaining to register & fetch the company")
public class CompanyConrtoller {

	@Autowired
	private CompanyService companyService;

	@ApiOperation(value = "Register company", response = CompanyDto.class)
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto companyDto) {
		return companyService.createCompany(companyDto);
	}

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

	@ApiOperation(value = "Delete company by code", response = Boolean.class)
	@RequestMapping(value = "/delete/{code}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Boolean> removeCompany(@PathVariable String code) {
		return companyService.removeCompany(code);
	}

	@ApiOperation(value = "View company latest stockprice by code", response = CompanyStockDto.class)
	@RequestMapping(value = "/view/{code}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<CompanyStockDto> viewCompany(@PathVariable String code) {
		return new ResponseEntity<CompanyStockDto>(companyService.viewCompanyLatestPrice(code), HttpStatus.OK);
	}

}