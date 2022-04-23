package com.stockmarketservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stockmarketservice.entity.Company;

public interface Companyrepository extends MongoRepository<Company, String>{

	Optional<Company> findByCompanyCode(String companyCode);

	Boolean deleteByCode(String code);

}
