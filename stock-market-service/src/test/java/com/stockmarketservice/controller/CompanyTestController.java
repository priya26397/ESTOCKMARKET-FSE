package com.stockmarketservice.controller;

import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.stockmarketservice.dto.CompanyDto;
import com.stockmarketservice.service.CompanyService;

@SpringBootTest
public class CompanyTestController {

	@InjectMocks
	private CompanyConrtoller companyConrtoller;
	
	@Mock
	private CompanyService companyService;
	
	@Test
	public void createCompanyTest() {
		CompanyDto companyDto=new CompanyDto();
		Mockito.when(companyService.createCompany(companyDto)).thenReturn(new ResponseEntity<CompanyDto>(companyDto,HttpStatus.CREATED));
		companyConrtoller.createCompany(companyDto);
	}
	@Test
	public void getCompanyTest() {
		CompanyDto companyDto=new CompanyDto();
		List<CompanyDto> comDtos=new ArrayList<>();
		comDtos.add(companyDto);
		Mockito.when(companyService.getCompany()).thenReturn(comDtos);
		companyConrtoller.getCompany();
	}
	@Test
	public void getCompanyIdTest() {
		CompanyDto companyDto=new CompanyDto();
		Mockito.when(companyService.getCompanyById(companyDto.getCompanyCode())).thenReturn(companyDto);
		companyConrtoller.getCompanyId(companyDto.getCompanyCode());
	}
	@Test
	public void removeCompanyTest() {
		
		Mockito.when(companyService.removeCompany(anyString())).thenReturn(new ResponseEntity<Boolean>(true,HttpStatus.OK));
		companyConrtoller.removeCompany(anyString());
	}
}
