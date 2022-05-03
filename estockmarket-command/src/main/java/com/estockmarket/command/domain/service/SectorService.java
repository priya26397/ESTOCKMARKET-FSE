package com.estockmarket.command.domain.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.estockmarket.command.application.dto.SectorDto;
import com.estockmarket.command.domain.entity.Sector;
import com.estockmarket.command.infrastructure.eventsourcing.KafkaSectorEventSourcing;
import com.estockmarket.command.infrastructure.repository.SectorRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class SectorService {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SectorRepository sectorRepository;

	@Autowired
	private KafkaSectorEventSourcing kafkaSectorEventSourcing;

	public ResponseEntity<SectorDto> createSector(SectorDto sectorDto) throws JsonProcessingException {
		Sector sector = convertToSector(sectorDto);
		Optional<Sector> sectorIsPresent = sectorRepository.findBySectorName(sectorDto.getSectorName());
		if (sectorIsPresent.isEmpty()) {
			Sector sectorResult = sectorRepository.save(sector);
			kafkaSectorEventSourcing.createSectorEvent(sectorResult);
			LOGGER.info("Sector saved successfully {}", sectorResult);
			return new ResponseEntity<SectorDto>(convertToSectorDto(sectorResult), HttpStatus.CREATED);
		}
		return new ResponseEntity<SectorDto>(new SectorDto(), HttpStatus.BAD_GATEWAY);
	}

	private SectorDto convertToSectorDto(Sector sectorResult) {
		SectorDto sectorDto = new SectorDto();
		sectorDto.setId(sectorResult.getId());
		sectorDto.setDescription(sectorResult.getDescription());
		sectorDto.setSectorName(sectorResult.getSectorName());
		return sectorDto;
	}

	private Sector convertToSector(SectorDto sectorDto) {
		Sector sector = new Sector();
		sector.setId(sectorDto.getId());
		sector.setDescription(sectorDto.getDescription());
		sector.setSectorName(sectorDto.getSectorName());
		return sector;
	}

	public ResponseEntity<Boolean> removeSector(Integer id) throws JsonProcessingException {
		Optional<Sector> sector = sectorRepository.findById(id);
		if (sector.isPresent()) {
			sectorRepository.deleteById(id);
			kafkaSectorEventSourcing.removeSectorEvent(sector.get());
			LOGGER.info("Sector removed successfully");
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
		return new ResponseEntity<>(false, HttpStatus.OK);
	}

}
