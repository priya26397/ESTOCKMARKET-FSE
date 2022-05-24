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

import com.estockmarket.query.application.controller.StockController;
import com.estockmarket.query.application.dto.StockAggregateDTO;
import com.estockmarket.query.application.dto.StockDto;
import com.estockmarket.query.domain.service.StockService;

@SpringBootTest
class StocksControllerTest {

	@InjectMocks
	private StockController stockController;

	@Mock
	private StockService stockService;

	@Test
	public void testGetCompanyStocks() throws ParseException {
		List<StockDto> stockList = new ArrayList<>();
		StockDto stock = new StockDto();
		stock.setCompanyCode("C-001");
		stock.setPrice(20.0);
		stockList.add(stock);
		Mockito.when(stockService.getCompanyStocks("C-001", "02-05-2022", "07-05-2022")).thenReturn(stockList);
		ResponseEntity<List<StockDto>> result = stockController.getCompanyStocks("C-001", "02-05-2022", "07-05-2022");
		Assertions.assertEquals(1, result.getBody().size());
	}

	@Test
	public void testGetCompanyAggregate() throws ParseException {
		List<StockAggregateDTO> stockList = new ArrayList<>();
		StockAggregateDTO stock = new StockAggregateDTO();
		stock.setCompanyCode("C001");
		stock.setMaxPrice(30.0);
		stock.setMinPrice(10.0);
		stockList.add(stock);
		Mockito.when(stockService.getStocksAggregate("C-001", "02-05-2022", "07-05-2022")).thenReturn(stockList);
		ResponseEntity<List<StockAggregateDTO>> result = stockController.getCompanyAggregate("C-001", "02-05-2022",
				"07-05-2022");
		Assertions.assertEquals(30.0, result.getBody().get(0).getMaxPrice());
	}

}
