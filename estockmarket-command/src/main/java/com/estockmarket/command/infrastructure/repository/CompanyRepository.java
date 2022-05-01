package com.estockmarket.command.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estockmarket.command.domain.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer>{

	Optional<Company> findByCompanyCode(String companyCode);

	long deleteByCompanyCode(String code);

}
