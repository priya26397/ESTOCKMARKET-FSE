package com.stockmarketservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stockmarketservice.entity.Sector;

public interface SectorRepository extends MongoRepository<Sector, String> {

	Optional<Sector> findBySectorName(String sectorName);

}
