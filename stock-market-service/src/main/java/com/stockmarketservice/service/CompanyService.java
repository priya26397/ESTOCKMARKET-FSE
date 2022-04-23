package com.stockmarketservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stockmarketservice.dto.CompanyDto;
import com.stockmarketservice.dto.CompanyStockDto;
import com.stockmarketservice.entity.Company;
import com.stockmarketservice.entity.Stocks;
import com.stockmarketservice.repository.Companyrepository;
import com.stockmarketservice.repository.StockRepository;



@Service
public class CompanyService {

	@Autowired
	private Companyrepository companyrepository;
	
	@Autowired
	private StockRepository stockRepository;
	
	public ResponseEntity<CompanyDto> createCompany(CompanyDto companyDto) {
		Company company=convertToCompany(companyDto);
		Optional<Company> companyIsPresent=companyrepository.findByCompanyCode(companyDto.getCompanyCode());
		Company companyresult =new Company();
		if(companyIsPresent.isEmpty()) {
			companyresult=companyrepository.save(company);
			return new ResponseEntity<CompanyDto>(convertToCompanyDto(companyresult),HttpStatus.CREATED);
		}
		return new ResponseEntity<CompanyDto>(new CompanyDto(),HttpStatus.BAD_GATEWAY);
				
	}
	
	private Company convertToCompany(CompanyDto companyDto) {
		Company company=new Company();
		if(companyDto.getCompanyCode()!=null) {
			company.setId(companyDto.getId());
		}
		company.setCompanyCode(companyDto.getCompanyCode());
		company.setCeo(companyDto.getCeo());
		company.setCompanyDescription(companyDto.getCompanyDescription());
		company.setCompanyName(companyDto.getCompanyName());
		company.setCompanyTurnover(companyDto.getCompanyTurnover());
		company.setCompanyWebsite(companyDto.getCompanyWebsite());
		company.setSectorId(companyDto.getSectorId());
		company.setStockExchangeName(companyDto.getStockExchangeName());
		return company;
	}

	public List<CompanyDto> getCompany() {
		List<Company> comList=companyrepository.findAll();
		List<CompanyDto> companyDtos= new ArrayList<>();
		comList.forEach(company->{
			CompanyDto companyDto=convertToCompanyDto(company);
			companyDtos.add(companyDto);
		});
		
		return companyDtos;
		
	}
	
	private CompanyDto convertToCompanyDto(Company company) {
		CompanyDto companyDto=new CompanyDto();
		companyDto.setCompanyCode(company.getId());
		companyDto.setCeo(company.getCeo());
		companyDto.setCompanyDescription(company.getCompanyDescription());
		companyDto.setCompanyName(company.getCompanyName());
		companyDto.setCompanyTurnover(company.getCompanyTurnover());
		companyDto.setCompanyWebsite(company.getCompanyWebsite());
		companyDto.setSectorId(company.getSectorId());
		companyDto.setStockExchangeName(company.getStockExchangeName());
		return companyDto;
	}

	public CompanyDto getCompanyById(String code) {
		Optional<Company> company=companyrepository.findByCompanyCode(code);
		CompanyDto companyDto=new CompanyDto();
		if(company.isPresent()) {
			companyDto=convertToCompanyDto(company.get());
		}
		return companyDto;
		
	}
	
	public ResponseEntity<Boolean> removeCompany(String code) {
		Optional<Company> company=companyrepository.findByCompanyCode(code);
		if(company.isPresent()) {
			Boolean flag=companyrepository.deleteByCode(code);
			return new ResponseEntity<>(flag,HttpStatus.OK);
		}
		return new ResponseEntity<>(false,HttpStatus.OK);
		
		
	}

	public CompanyStockDto viewCompanyLatestPrice(String code) {
		CompanyStockDto companyStockDto=new CompanyStockDto();
		Optional<Company> company=companyrepository.findByCompanyCode(code);
		if(company.isPresent()) {
			Stocks stock=stockRepository.findByCompanyCodeAndFirstByOrderBycreatedDateDateDesc(code);
			converToCompanyStockDto(company.get(),stock,companyStockDto);
		}
		
		return companyStockDto;
	}

	private void converToCompanyStockDto(Company company, Stocks stock, CompanyStockDto companyStockDto) {
		companyStockDto.setId(company.getId());
		companyStockDto.setCompanyCode(company.getId());
		companyStockDto.setCeo(company.getCeo());
		companyStockDto.setCompanyDescription(company.getCompanyDescription());
		companyStockDto.setCompanyName(company.getCompanyName());
		companyStockDto.setCompanyTurnover(company.getCompanyTurnover());
		companyStockDto.setCompanyWebsite(company.getCompanyWebsite());
		companyStockDto.setSectorId(company.getSectorId());
		companyStockDto.setStockExchangeName(company.getStockExchangeName());
		companyStockDto.setLatestStockPrice(stock.getPrice());
	}
	
}
