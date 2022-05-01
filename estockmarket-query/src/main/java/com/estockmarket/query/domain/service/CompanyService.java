package com.estockmarket.query.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estockmarket.query.application.dto.CompanyDto;
import com.estockmarket.query.application.dto.CompanyStockDto;
import com.estockmarket.query.domain.exception.InvalidCompanyCodeException;
import com.estockmarket.query.domain.exception.NoStocksExistsException;
import com.estockmarket.query.domain.model.Company;
import com.estockmarket.query.domain.model.Stocks;
import com.estockmarket.query.infrastructure.repository.Companyrepository;
import com.estockmarket.query.infrastructure.repository.StockRepository;

@Service
public class CompanyService {

	@Autowired
	private Companyrepository companyrepository;

	@Autowired
	private StockRepository stockRepository;

	public List<CompanyDto> getCompany() {
		List<Company> comList = companyrepository.findAll();
		List<CompanyDto> companyDtos = new ArrayList<>();
		comList.forEach(company -> {
			CompanyDto companyDto = convertToCompanyDto(company);
			companyDtos.add(companyDto);
		});

		return companyDtos;

	}

	private CompanyDto convertToCompanyDto(Company company) {
		CompanyDto companyDto = new CompanyDto();
		companyDto.setId(company.getId());
		companyDto.setCompanyCode(company.getCompanyCode());
		companyDto.setCeo(company.getCeo());
		companyDto.setCompanyDescription(company.getCompanyDescription());
		companyDto.setCompanyName(company.getCompanyName());
		companyDto.setCompanyTurnover(company.getCompanyTurnover());
		companyDto.setCompanyWebsite(company.getCompanyWebsite());
		companyDto.setSectorName(company.getSectorName());
		companyDto.setStockExchangeName(company.getStockExchangeName());
		companyDto.setCompanyDescription(company.getCompanyDescription());
		return companyDto;
	}

	public CompanyDto getCompanyById(String code) {
		Optional<Company> company = companyrepository.findByCompanyCode(code);
		CompanyDto companyDto = new CompanyDto();
		if (company.isPresent()) {
			companyDto = convertToCompanyDto(company.get());
			return companyDto;
		}
		throw new InvalidCompanyCodeException();

	}

	public CompanyStockDto viewCompanyLatestPrice(String code) {
		CompanyStockDto companyStockDto = new CompanyStockDto();
		Optional<Company> company = companyrepository.findByCompanyCode(code);
		if (company.isPresent()) {
			Optional<Stocks> stock = stockRepository.findFirstByCompanyCodeOrderByUpdatedOnDesc(code);
			if(stock.isPresent()) {
				converToCompanyStockDto(company.get(), stock.get(), companyStockDto);
			}
			throw new NoStocksExistsException();
		}

		return companyStockDto;
	}

	private void converToCompanyStockDto(Company company, Stocks stock, CompanyStockDto companyStockDto) {
		companyStockDto.setId(company.getId());
		companyStockDto.setCompanyCode(company.getCompanyCode());
		companyStockDto.setCeo(company.getCeo());
		companyStockDto.setCompanyDescription(company.getCompanyDescription());
		companyStockDto.setCompanyName(company.getCompanyName());
		companyStockDto.setCompanyTurnover(company.getCompanyTurnover());
		companyStockDto.setCompanyWebsite(company.getCompanyWebsite());
		companyStockDto.setSectorName(company.getSectorName());
		companyStockDto.setStockExchangeName(company.getStockExchangeName());
		companyStockDto.setLatestStockPrice(stock.getPrice());
	}

	public void createCompany(Company company) {
		companyrepository.save(company);
	}

	public void removeCompany(Long id) {
		companyrepository.deleteById(id);
	}

}