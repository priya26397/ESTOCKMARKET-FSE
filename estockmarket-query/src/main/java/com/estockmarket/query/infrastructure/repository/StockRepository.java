package com.estockmarket.query.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.estockmarket.query.domain.model.Stocks;

@Repository
public interface StockRepository extends MongoRepository<Stocks, Long> {

	Optional<Stocks> findFirstByCompanyCodeOrderByUpdatedOnDesc(String companyCode);

}
