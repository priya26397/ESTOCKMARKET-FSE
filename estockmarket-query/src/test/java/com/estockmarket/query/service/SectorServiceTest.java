package com.estockmarket.query.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.estockmarket.query.domain.model.Sector;
import com.estockmarket.query.domain.service.SectorService;
import com.estockmarket.query.infrastructure.repository.SectorRepository;

@SpringBootTest
public class SectorServiceTest {

	@InjectMocks
	private SectorService sectorService;

	@Mock
	private SectorRepository sectorRepository;

	@Test
	public void testGetSector() throws ParseException {
		List<Sector> list = new ArrayList<Sector>();
		Mockito.when(sectorRepository.findAll()).thenReturn(list);
		sectorService.getSector();
	}

}
