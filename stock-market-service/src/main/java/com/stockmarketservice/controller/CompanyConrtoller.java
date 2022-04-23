package com.stockmarketservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockmarketservice.dto.CompanyDto;
import com.stockmarketservice.dto.CompanyStockDto;
import com.stockmarketservice.service.CompanyService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("market/company")
public class CompanyConrtoller {

	@Autowired
	private CompanyService companyService;

	@PostMapping("/")
	public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto companyDto) {
		return  companyService.createCompany(companyDto);
	}

	@GetMapping("/")
	public ResponseEntity<List<CompanyDto>> getCompany(@RequestBody CompanyDto companyDto) {
		return new ResponseEntity<List<CompanyDto>>(companyService.getCompany(),HttpStatus.OK);
	}

	@PostMapping("/{code}")
	public ResponseEntity<CompanyDto> getCompanyId(@PathVariable String code) {
		return new ResponseEntity<CompanyDto>(companyService.getCompanyById(code),HttpStatus.OK);
	}

	@DeleteMapping("/{code}")
	public ResponseEntity<Boolean> removeCompany(@PathVariable String code) {
		return companyService.removeCompany(code);
	}
	
	@GetMapping("view/{code}")
	public ResponseEntity<CompanyStockDto> viewCompany(@PathVariable String code){
		return  new ResponseEntity<CompanyStockDto>(companyService.viewCompanyLatestPrice(code),HttpStatus.OK);
	}

}
