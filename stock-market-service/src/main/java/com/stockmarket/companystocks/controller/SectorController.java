package com.stockmarket.companystocks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.companystocks.dto.SectorDto;
import com.stockmarket.companystocks.service.SectorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/${api.version}/market/sector")
@Api(value="user", description="Operations pertaining to add and fetch sector for the company")
public class SectorController {
	@Autowired
	private SectorService sectorService;

	@ApiOperation(value = "Create a sector",response = SectorDto.class)    
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<SectorDto> createSector(@RequestBody SectorDto sectorDto) {
		return sectorService.createSector(sectorDto);
	}

	@ApiOperation(value = "Fetch all sectors",response = List.class)    
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<SectorDto>> getSector() {
		return new ResponseEntity<List<SectorDto>>(sectorService.getSector(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Remove a sector",response = Boolean.class)    
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Boolean> removeSector(@PathVariable String id) {
		return sectorService.removeSector(id);
	}

}
