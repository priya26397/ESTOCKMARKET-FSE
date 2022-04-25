package com.stockmarket.companystocks.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stockmarket.companystocks.dto.StockAggregateDTO;
import com.stockmarket.companystocks.dto.StockDto;
import com.stockmarket.companystocks.entity.Stocks;
import com.stockmarket.companystocks.repository.StockRepository;

@Service
public class StockService {

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private MongoTemplate mongoTemplate;

	public StockDto createStock(StockDto stockDto) {
		Stocks stocks = (Stocks) modelMapper.map(stockDto, Stocks.class);
		stocks.setId(sequenceGeneratorService.generateSequence(Stocks.SEQUENCE_NAME));
		Stocks stocksResult = stockRepository.save(stocks);
		return (StockDto) modelMapper.map(stocksResult, StockDto.class);
	}

	public List<StockDto> getCompanyStocks(String companyCode, String startDate, String endDate) {
		LocalDate intialDat = LocalDate.parse(startDate);
		LocalDate lastDate = LocalDate.parse(endDate);
		Query query = new Query();
		query.addCriteria(getStockQuery(companyCode, intialDat, lastDate));
		System.out.println(query);
		List<Stocks> stocksList = mongoOperations.find(query, Stocks.class);
		List<StockDto> stockDtos = new ArrayList<>();
		stocksList.forEach(stocks -> {
			StockDto stockDto = (StockDto) modelMapper.map(stocks, StockDto.class);
			stockDtos.add(stockDto);
		});
		return stockDtos;
	}

	private Criteria getStockQuery(String companyCode, LocalDate startDate, LocalDate endDate) {
		Criteria criteria = Criteria.where("companyCode").is(companyCode);
		if (startDate != null && endDate != null) {
			criteria.andOperator(Criteria.where("updatedOn").gte(startDate).lte(endDate));
		}
		return criteria;
	}

	public ResponseEntity<Boolean> deleteCompanyStocks(String companyCode) {
		List<Stocks> stocks = stockRepository.findByCompanyCode(companyCode);
		if (stocks.size() > 0) {
			stockRepository.deleteByCompanyCode(companyCode);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.OK);

	}

	public List<StockAggregateDTO> getStocksAggregate(String companyCode, String startDate, String endDate) {
		MatchOperation matchStage = Aggregation
				.match(getStockQuery(companyCode, LocalDate.parse(startDate), LocalDate.parse(endDate)));
		GroupOperation group = Aggregation.group("companyCode").first("companyCode").as("companyCode").min("price")
				.as("minPrice").max("price").as("maxPrice").avg("price").as("avgPrice");
		Aggregation aggregation = Aggregation.newAggregation(matchStage,group);

		AggregationResults<StockAggregateDTO> output = mongoTemplate.aggregate(aggregation, Stocks.class,
				StockAggregateDTO.class);
		return output != null ? output.getMappedResults() : null;
	}

	@SuppressWarnings("unchecked")
	private Object map(Object object, @SuppressWarnings("rawtypes") Class clazz) {
		return object != null ? modelMapper.map(object, clazz) : null;
	}

	public StockDto updateStock(StockDto stockDto) {
		Optional<Stocks> stockDtoFetch=stockRepository.findById(stockDto.getId());
		Stocks stockResult=new Stocks();
		if(stockDtoFetch.isPresent()) {
			stockDtoFetch.get().setCompanyCode(stockDto.getCompanyCode());
			stockDtoFetch.get().setPrice(stockDto.getPrice());
			stockResult=stockRepository.save(stockDtoFetch.get());
		}
		return (StockDto) modelMapper.map(stockResult, StockDto.class);
		
	}
}
