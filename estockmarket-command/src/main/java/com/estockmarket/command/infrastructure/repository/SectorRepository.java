package com.estockmarket.command.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estockmarket.command.domain.entity.Sector;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Integer>{

	Optional<Sector> findBySectorName(String sectorName);

}
