package com.stockmarket.companystocks.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stockmarket.companystocks.entity.Company;

@Repository
public interface Companyrepository extends MongoRepository<Company, String>{

	Optional<Company> findByCompanyCode(String companyCode);

	Long deleteByCompanyCode(String code);

}
