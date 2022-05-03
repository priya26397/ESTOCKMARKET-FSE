package com.estockmarket.command.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.estockmarket.command.application.dto.CompanyDto;
import com.estockmarket.command.domain.entity.Company;
import com.estockmarket.command.domain.exception.CompanyAlreadyExistsExcetion;
import com.estockmarket.command.infrastructure.eventsourcing.KafkaCompanyEventSourcing;
import com.estockmarket.command.infrastructure.repository.CompanyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class CompanyService {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private KafkaCompanyEventSourcing kafkaCompanyEventSourcing;

	public ResponseEntity<CompanyDto> createCompany(CompanyDto companyDto) throws JsonProcessingException {
		Company company = convertToCompany(companyDto);
		Optional<Company> companyIsPresent = companyRepository.findByCompanyCode(companyDto.getCompanyCode());
		Company companyresult = new Company();
		if (companyIsPresent.isEmpty()) {
			companyresult = companyRepository.save(company);
			LOGGER.info("Company details saved successfully {}",company);
			kafkaCompanyEventSourcing.createCompanyEvent(companyresult);
			return new ResponseEntity<CompanyDto>(convertToCompanyDto(companyresult), HttpStatus.CREATED);
		}
		throw new CompanyAlreadyExistsExcetion();

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


	@Transactional
	public ResponseEntity<Boolean> removeCompany(String code) throws JsonProcessingException  {
		Optional<Company> company = companyRepository.findByCompanyCode(code);
		if (company.isPresent()) {
			long value = companyRepository.deleteByCompanyCode(code);
			kafkaCompanyEventSourcing.removeCompanyEvent(company.get());
			LOGGER.info("Company details removed from DB {}",company);
			if(value >= 1) {
				return new ResponseEntity<>(true, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(false, HttpStatus.OK);

	}


}
