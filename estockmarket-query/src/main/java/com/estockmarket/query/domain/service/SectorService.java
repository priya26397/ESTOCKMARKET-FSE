package com.estockmarket.query.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estockmarket.query.application.dto.SectorDto;
import com.estockmarket.query.domain.model.Sector;
import com.estockmarket.query.infrastructure.repository.SectorRepository;

@Service
public class SectorService {
	
	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SectorRepository sectorRepository;

	private SectorDto convertToSectorDto(Sector sectorResult) {
		LOGGER.info("convert to sector Dto {}", sectorResult);
		SectorDto sectorDto = new SectorDto();
		sectorDto.setId(sectorResult.getId());
		sectorDto.setDescription(sectorResult.getDescription());
		sectorDto.setSectorName(sectorResult.getSectorName());
		return sectorDto;
	}

	public List<SectorDto> getSector() {
		LOGGER.info("fetch all sector {}");
		List<Sector> sectorList = sectorRepository.findAll();
		List<SectorDto> sectorDtos = new ArrayList<>();
		sectorList.forEach(sector -> {
			SectorDto sectorDto = convertToSectorDto(sector);
			sectorDtos.add(sectorDto);
		});

		return sectorDtos;
	}

	public void createSector(Sector sector) {
		LOGGER.info("create sector{}", sector);
		sectorRepository.save(sector);
	}

	public void removeSector(Long id) {
		LOGGER.info("remove sector based on id {}",id);
		sectorRepository.deleteById(id);
	}

}
