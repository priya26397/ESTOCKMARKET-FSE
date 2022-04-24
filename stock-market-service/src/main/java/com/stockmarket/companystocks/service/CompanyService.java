package com.stockmarket.companystocks.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stockmarket.companystocks.dto.CompanyDto;
import com.stockmarket.companystocks.dto.CompanyStockDto;
import com.stockmarket.companystocks.entity.Company;
import com.stockmarket.companystocks.entity.Stocks;
import com.stockmarket.companystocks.exception.InvalidCompanyCodeException;
import com.stockmarket.companystocks.repository.Companyrepository;
import com.stockmarket.companystocks.repository.StockRepository;

@Service
public class CompanyService {

	@Autowired
	private Companyrepository companyrepository;

	@Autowired
	private StockRepository stockRepository;

	public ResponseEntity<CompanyDto> createCompany(CompanyDto companyDto) {
		Company company = convertToCompany(companyDto);
		Optional<Company> companyIsPresent = companyrepository.findByCompanyCode(companyDto.getCompanyCode());
		Company companyresult = new Company();
		if (companyIsPresent.isEmpty()) {
			companyresult = companyrepository.save(company);
			return new ResponseEntity<CompanyDto>(convertToCompanyDto(companyresult), HttpStatus.CREATED);
		}
		return new ResponseEntity<CompanyDto>(new CompanyDto(), HttpStatus.BAD_GATEWAY);

	}

	private Company convertToCompany(CompanyDto companyDto) {
		Company company = new Company();
		if (companyDto.getCompanyCode() != null) {
			company.setId(companyDto.getId());
		}
		company.setCompanyCode(companyDto.getCompanyCode());
		company.setCeo(companyDto.getCeo());
		company.setCompanyDescription(companyDto.getCompanyDescription());
		company.setCompanyName(companyDto.getCompanyName());
		company.setCompanyTurnover(companyDto.getCompanyTurnover());
		company.setCompanyWebsite(companyDto.getCompanyWebsite());
		company.setSectorName(companyDto.getSectorName());
		company.setStockExchangeName(companyDto.getStockExchangeName());
		company.setCompanyDescription(companyDto.getCompanyDescription());
		return company;
	}

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
		}
		throw new InvalidCompanyCodeException();

	}

	public ResponseEntity<Boolean> removeCompany(String code) {
		Optional<Company> company = companyrepository.findByCompanyCode(code);
		if (company.isPresent()) {
			long value = companyrepository.deleteByCompanyCode(code);
			if(value >= 1) {
				return new ResponseEntity<>(true, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(false, HttpStatus.OK);

	}

	public CompanyStockDto viewCompanyLatestPrice(String code) {
		CompanyStockDto companyStockDto = new CompanyStockDto();
		Optional<Company> company = companyrepository.findByCompanyCode(code);
		if (company.isPresent()) {
			Stocks stock = stockRepository.findFirstByCompanyCodeOrderByUpdatedOnDesc(code);
			converToCompanyStockDto(company.get(), stock, companyStockDto);
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

}