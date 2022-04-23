package com.stockmarketservice.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stockmarketservice.dto.StockAggregateDTO;
import com.stockmarketservice.dto.StockDto;
import com.stockmarketservice.entity.Stocks;
import com.stockmarketservice.repository.StockRepository;

@Service
public class StockService {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private MongoTemplate mongoTemplate;

	public StockDto createStock(StockDto stockDto) {
		Stocks stocks = convertToStock(stockDto);
		Stocks stocksResult=stockRepository.save(stocks);
		return convertToStockDto(stocksResult);
	}

	private Stocks convertToStock(StockDto stockDto) {
		Stocks stocks = new Stocks();
		stocks.setCompanyCode(stockDto.getCompanyCode());
		stocks.setPrice(stockDto.getPrice());
		return stocks;
	}

	public List<StockDto> getStocks(String companyCode, String startDate, String endDate) {
		LocalDate intialDat = LocalDate.parse(startDate);
		LocalDate lastDate = LocalDate.parse(startDate);
		Query query = new Query();
		query.addCriteria(getStockQuery(companyCode, intialDat, lastDate));
		List<Stocks> stocksList = mongoOperations.find(query, Stocks.class);
		List<StockDto> stockDtos = new ArrayList<>();
		stocksList.forEach(stocks -> {
			StockDto stockDto = convertToStockDto(stocks);
			stockDtos.add(stockDto);
		});
		return stockDtos;
	}

	private StockDto convertToStockDto(Stocks stocks) {
		StockDto stockDto = new StockDto();
		stockDto.setId(stocks.getId());
		stockDto.setCompanyCode(stocks.getCompanyCode());
		stockDto.setPrice(stocks.getPrice());
		stockDto.setCreatedDate(stocks.getCreatedDate());
		stockDto.setLastModifiedDate(stocks.getLastModifiedDate());
		return stockDto;

	}

	private Criteria getStockQuery(String companyCode, LocalDate startDate, LocalDate endDate) {
		Criteria criteria = Criteria.where("companyCode").is(companyCode);
		if (startDate != null && endDate != null) {
			criteria.andOperator(Criteria.where("date").gte(startDate).lte(endDate));
		}
		return criteria;
	}

	public ResponseEntity<Boolean> deleteStocks(String companyCode) {
		List<Stocks> stocks=stockRepository.findByCompanyCode(companyCode);
		if(stocks.size()>0) {
			stockRepository.deleteByCompanyCode(companyCode);
			return new ResponseEntity<Boolean>(true,HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(false,HttpStatus.OK);

	}

	public List<StockAggregateDTO> getStocksAggregate(String companyCode, String startDate, String endDate) {
		MatchOperation matchStage = Aggregation
				.match(getStockQuery(companyCode, LocalDate.parse(startDate), LocalDate.parse(startDate)));
		GroupOperation group = Aggregation.group("companyCode").first("companyCode").as("companyCode").min("price")
				.as("minPrice").max("price").as("maxPrice").avg("price").as("avgPrice");

		Aggregation aggregation = Aggregation.newAggregation(matchStage, group);

		AggregationResults<StockAggregateDTO> output = mongoTemplate.aggregate(aggregation, Stocks.class,
				StockAggregateDTO.class);
		return output != null ? output.getMappedResults() : null;
	}

}
