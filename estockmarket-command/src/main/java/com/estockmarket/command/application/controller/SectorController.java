package com.estockmarket.command.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estockmarket.command.application.dto.SectorDto;
import com.estockmarket.command.domain.service.SectorService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/${api.version}/command/market/sector")
@Api(value="sector", description="Operations pertaining to add and delete sector for the company")
public class SectorController {
	@Autowired
	private SectorService sectorService;

	@ApiOperation(value = "Create a sector",response = SectorDto.class)    
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<SectorDto> createSector(@RequestBody SectorDto sectorDto) throws JsonProcessingException {
		return sectorService.createSector(sectorDto);
	}
	
	@ApiOperation(value = "Remove a sector",response = Boolean.class)    
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Boolean> removeSector(@PathVariable String id) throws JsonProcessingException, NumberFormatException {
		return sectorService.removeSector(Integer.parseInt(id));
	}

}
