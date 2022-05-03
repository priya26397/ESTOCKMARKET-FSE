package com.estockmarket.command.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.estockmarket.command.application.dto.StockDto;
import com.estockmarket.command.domain.entity.Stocks;
import com.estockmarket.command.infrastructure.eventsourcing.KafkaStocksEventSourcing;
import com.estockmarket.command.infrastructure.repository.StockRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class StockService {
	
	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private KafkaStocksEventSourcing kafkaStocksEventSourcing;

	public StockDto createStock(StockDto stockDto) throws JsonProcessingException {
		Stocks stocks = (Stocks) modelMapper.map(stockDto, Stocks.class);
		Stocks stocksResult = stockRepository.save(stocks);
		kafkaStocksEventSourcing.createStocksEvent(stocksResult);
		LOGGER.info("Stocks saved successfully {}", stocksResult);
		return (StockDto) modelMapper.map(stocksResult, StockDto.class);
	}

	@Transactional
	public ResponseEntity<Boolean> deleteCompanyStocks(Long id) throws JsonProcessingException {
		Optional<Stocks> stocks = stockRepository.findById(id);
		if (stocks.isPresent()) {
			stockRepository.deleteById(id);
			kafkaStocksEventSourcing.removeStocksEvent(stocks.get());
			LOGGER.info("Stocks removed successfully");
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.OK);

	}

	@SuppressWarnings("unchecked")
	private Object map(Object object, @SuppressWarnings("rawtypes") Class clazz) {
		return object != null ? modelMapper.map(object, clazz) : null;
	}

	public StockDto updateStock(StockDto stockDto) throws JsonProcessingException {
		Optional<Stocks> stockDtoFetch = stockRepository.findById(stockDto.getId());
		Stocks stockResult = new Stocks();
		if (stockDtoFetch.isPresent()) {
			stockDtoFetch.get().setCompanyCode(stockDto.getCompanyCode());
			stockDtoFetch.get().setPrice(stockDto.getPrice());
			stockResult = stockRepository.save(stockDtoFetch.get());
			LOGGER.info("Stocks updated successfully {}",stockResult);
			kafkaStocksEventSourcing.updateStocksEvent(stockResult);
		}
		return (StockDto) modelMapper.map(stockResult, StockDto.class);

	}
}
