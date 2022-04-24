package com.stockmarket.companystocks.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stockmarket.companystocks.entity.Stocks;

@Repository
public interface StockRepository extends MongoRepository<Stocks, Long>{

	void deleteByCompanyCode(String companyCode);

	List<Stocks> findByCompanyCode(String companyCode);
	
	Stocks findFirstByCompanyCodeOrderByUpdatedOnDesc(String companyCode);

}
