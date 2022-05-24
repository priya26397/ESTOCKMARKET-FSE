package com.estockmarket.query.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.estockmarket.query.application.controller.CompanyController;
import com.estockmarket.query.application.dto.CompanyDto;
import com.estockmarket.query.application.dto.CompanyStockDto;
import com.estockmarket.query.domain.service.CompanyService;

@SpringBootTest
class CompanyControllerTest {

	@InjectMocks
	private CompanyController companyController;

	@Mock
	private CompanyService companyService;
	
	@Test
	public void testGetCompany() {
		List<CompanyDto> companyList = new ArrayList<>();
		CompanyDto company = new CompanyDto();
		company.setCompanyCode("C001");
		company.setCompanyName("TCS");
		companyList.add(company);
		Mockito.when(companyService.getCompany()).thenReturn(companyList);
		ResponseEntity<List<CompanyDto>> result = companyController.getCompany();
		Assertions.assertEquals(1, result.getBody().size());		
	}
	
	@Test
	public void testGetCompanyId() {
		CompanyDto company = new CompanyDto();
		company.setCompanyCode("C001");
		company.setCompanyName("TCS");
		Mockito.when(companyService.getCompanyById("C001")).thenReturn(company);
		ResponseEntity<CompanyDto> result = companyController.getCompanyId("C001");
		Assertions.assertEquals("TCS", result.getBody().getCompanyName());		
	}
	
	@Test
	public void testViewCompany() {
		CompanyStockDto companyStock = new CompanyStockDto();
		companyStock.setCompanyCode("C001");
		companyStock.setLatestStockPrice(20.0);
		Mockito.when(companyService.viewCompanyLatestPrice("C001")).thenReturn(companyStock);
		ResponseEntity<CompanyStockDto> result = companyController.viewCompany("C001");
		Assertions.assertEquals(20.0, result.getBody().getLatestStockPrice());	
	}

}
