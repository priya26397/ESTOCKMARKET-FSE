package com.estockmarket.query.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.estockmarket.query.domain.exception.NoStocksExistsException;
import com.estockmarket.query.domain.model.Company;
import com.estockmarket.query.domain.model.Stocks;
import com.estockmarket.query.domain.service.CompanyService;
import com.estockmarket.query.infrastructure.repository.Companyrepository;
import com.estockmarket.query.infrastructure.repository.StockRepository;

@SpringBootTest
public class CompanyServiceTest {

	@InjectMocks
	private CompanyService companyService;

	@Mock
	private Companyrepository companyrepository;

	@Mock
	private StockRepository stockRepository;

	@Test
	public void testGetCompany() {
		Mockito.when(companyrepository.save(new Company())).thenReturn(Mockito.any());
		companyService.createCompany(new Company());
	}

	@Test
	public void testGetCompanyById() {
		Mockito.<Optional<Company>>when(companyrepository.findByCompanyCode("C-001")).thenReturn(Optional.of(new Company()));
		companyService.getCompanyById("C-001");
	}

	@Test
	public void testViewCompanyLatestPrice() {
		Mockito.<Optional<Company>>when(companyrepository.findByCompanyCode("C-001")).thenReturn(Optional.of(new Company()));
		Mockito.<Optional<Stocks>>when(stockRepository.findFirstByCompanyCodeOrderByUpdatedOnDesc("C-001")).thenReturn(Optional.of(new Stocks()));
		companyService.viewCompanyLatestPrice("C-001");
	}

}
