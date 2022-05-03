package com.estockmarket.command.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estockmarket.command.application.dto.CompanyDto;
import com.estockmarket.command.domain.service.CompanyService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/${api.version}/command/market/company")
@Api(value = "company", description = "Operations pertaining to register and remove the company")
public class CompanyController {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CompanyService companyService;

	@ApiOperation(value = "Register company", response = CompanyDto.class)
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto companyDto) throws JsonProcessingException {
		LOGGER.info("Create company details {}", companyDto);
		return companyService.createCompany(companyDto);
	}

	@ApiOperation(value = "Delete company by code", response = Boolean.class)
	@RequestMapping(value = "/delete/{code}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Boolean> removeCompany(@PathVariable String code)
			throws JsonProcessingException, NumberFormatException {
		LOGGER.info("Remove company details {}", code);
		return companyService.removeCompany(code);
	}

}