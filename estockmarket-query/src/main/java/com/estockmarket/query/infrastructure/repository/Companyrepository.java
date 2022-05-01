package com.estockmarket.query.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.estockmarket.query.domain.model.Company;

@Repository
public interface Companyrepository extends MongoRepository<Company, Long> {

	Optional<Company> findByCompanyCode(String companyCode);

}
