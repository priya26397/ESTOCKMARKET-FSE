package com.estockmarket.query.service;

import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import com.estockmarket.query.application.dto.StockAggregateDTO;
import com.estockmarket.query.domain.model.Stocks;
import com.estockmarket.query.domain.service.StockService;
import com.estockmarket.query.infrastructure.repository.Companyrepository;
import com.estockmarket.query.infrastructure.repository.StockRepository;

@SpringBootTest
public class StockServiceTest {

	@InjectMocks
	private StockService stockService;

	@Mock
	private Companyrepository companyrepository;

	@Mock
	private StockRepository stockRepository;

	@Mock
	private MongoOperations mongoOperations;

	@Mock
	private MongoTemplate mongoTemplate;

	@Test
	public void testGetCompanyStocks() throws ParseException {
		Mockito.when(mongoOperations.find(null, Stocks.class)).thenReturn(null);
		stockService.getCompanyStocks("C-001", "02-05-2022", "07-05-2022");
	}

	@Test
	public void testGetStocksAggregate() throws ParseException {
		MongoTemplate mongoTemplate = Mockito.mock(MongoTemplate.class);
		Aggregation aggregation = Mockito.mock(Aggregation.class);
		AggregationResults<StockAggregateDTO> aggregationResultsMock = Mockito.mock(AggregationResults.class);
		Mockito.when(aggregationResultsMock.toString()).thenReturn(new String());
		Mockito.doReturn(aggregationResultsMock).when(mongoTemplate).aggregate(aggregation, Stocks.class,
				StockAggregateDTO.class);
		stockService.getCompanyStocks("C-001", "02-05-2022", "07-05-2022");
	}

}
