package com.stockmarketservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockmarketservice.dto.SectorDto;
import com.stockmarketservice.service.SectorService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("market/company")
public class SectorController {
	@Autowired
	private SectorService sectorService;

	@PostMapping("/")
	public ResponseEntity<SectorDto> createSector(@RequestBody SectorDto sectorDto) {
		return  sectorService.createSector(sectorDto);
	}

	@GetMapping("/")
	public ResponseEntity<List<SectorDto>> getCompany(@RequestBody SectorDto companyDto) {
		return new ResponseEntity<List<SectorDto>>(sectorService.getSecotor(),HttpStatus.OK);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> removeCompany(@PathVariable String id) {
		return sectorService.removeCompany(id);
	}
	
}
