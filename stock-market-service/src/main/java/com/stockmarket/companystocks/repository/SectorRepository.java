package com.stockmarket.companystocks.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stockmarket.companystocks.entity.Sector;

@Repository
public interface SectorRepository extends MongoRepository<Sector, String> {

	Optional<Sector> findBySectorName(String sectorName);

}
