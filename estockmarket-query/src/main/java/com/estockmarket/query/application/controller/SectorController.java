package com.estockmarket.query.application.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estockmarket.query.application.dto.SectorDto;
import com.estockmarket.query.domain.service.SectorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/${api.version}/query/market/sector")
@Api(value = "sectors", description = "Operations pertaining to fetch sector for the company")
public class SectorController {
	
	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SectorService sectorService;

	@ApiOperation(value = "Fetch all sectors", response = List.class)
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<SectorDto>> getSector() {
		LOGGER.info("fetch all sector");
		return new ResponseEntity<List<SectorDto>>(sectorService.getSector(), HttpStatus.OK);
	}

}
