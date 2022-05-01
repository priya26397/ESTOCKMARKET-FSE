package com.estockmarket.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estockmarket.command.domain.entity.Stocks;

@Repository
public interface StockRepository extends JpaRepository<Stocks, Long> {

}
