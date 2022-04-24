package com.stockmarket.companystocks.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stockmarket.companystocks.dto.SectorDto;
import com.stockmarket.companystocks.entity.Sector;
import com.stockmarket.companystocks.repository.SectorRepository;

@Service
public class SectorService {

	@Autowired
	private SectorRepository sectorRepository;

	public ResponseEntity<SectorDto> createSector(SectorDto sectorDto) {
		Sector sector = convertToSector(sectorDto);
		Optional<Sector> sectorIsPresent = sectorRepository.findBySectorName(sectorDto.getSectorName());
		if (sectorIsPresent.isEmpty()) {
			Sector sectorResult = sectorRepository.save(sector);
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

	public List<SectorDto> getSector() {
		List<Sector> sectorList = sectorRepository.findAll();
		List<SectorDto> sectorDtos = new ArrayList<>();
		sectorList.forEach(sector -> {
			SectorDto sectorDto = convertToSectorDto(sector);
			sectorDtos.add(sectorDto);
		});

		return sectorDtos;
	}

	public ResponseEntity<Boolean> removeSector(String id) {
		Optional<Sector> sector = sectorRepository.findById(id);
		if (sector.isPresent()) {
			sectorRepository.deleteById(id);
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
		return new ResponseEntity<>(false, HttpStatus.OK);
	}

}
