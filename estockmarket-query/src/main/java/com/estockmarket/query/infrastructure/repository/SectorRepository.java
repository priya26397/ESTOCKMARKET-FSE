package com.estockmarket.query.infrastructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.estockmarket.query.domain.model.Sector;

@Repository
public interface SectorRepository extends MongoRepository<Sector, Long> {

}
