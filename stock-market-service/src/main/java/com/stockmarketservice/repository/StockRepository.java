package com.stockmarketservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stockmarketservice.entity.Stocks;

public interface StockRepository extends MongoRepository<Stocks, String>{

	void deleteByCompanyCode(String companyCode);

	List<Stocks> findByCompanyCode(String companyCode);
	
	Stocks findByCompanyCodeAndFirstByOrderBycreatedDateDateDesc(String companyCode);

}
