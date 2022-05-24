package com.estockmarket.query.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.estockmarket.query.application.controller.SectorController;
import com.estockmarket.query.application.dto.SectorDto;
import com.estockmarket.query.domain.service.SectorService;

@SpringBootTest
class SectorControllerTest {

	@InjectMocks
	private SectorController sectorController;

	@Mock
	private SectorService sectorService;

	@Test
	public void testGetSector() throws ParseException {
		List<SectorDto> sectorList = new ArrayList<>();
		SectorDto sector = new SectorDto();
		sector.setId(Long.parseLong("1"));
		sector.setSectorName("IT");
		sectorList.add(sector);
		Mockito.when(sectorService.getSector()).thenReturn(sectorList);
		ResponseEntity<List<SectorDto>> result = sectorController.getSector();
		Assertions.assertEquals(1, result.getBody().size());
	}

}
