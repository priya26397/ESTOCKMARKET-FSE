package com.estockmarket.command.infrastructure.eventsourcing;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.estockmarket.command.domain.entity.Stocks;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class KafkaStocksEventSourcing {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value(value = "${message.topic.createStock}")
	private String createTopicName;
	
	@Value(value = "${message.topic.updateStock}")
	private String updateTopicName;

	@Value(value = "${message.topic.removeStock}")
	private String removeTopicName;

	public void createStocksEvent(Stocks stocks) throws JsonProcessingException {
		/**Stocks newStock=new Stocks();
		newStock.setId(stocks.getId());
		newStock.setCompanyCode(stocks.getCompanyCode());
		newStock.setPrice(stocks.getPrice());**/
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = objectWriter.writeValueAsString(stocks);
		System.out.println(json);
		kafkaTemplate.send(createTopicName, json);
	}
	
	public void updateStocksEvent(Stocks stocks) throws JsonProcessingException {
		/**Stocks newStock=new Stocks();
		newStock.setId(stocks.getId());
		newStock.setCompanyCode(stocks.getCompanyCode());
		newStock.setPrice(stocks.getPrice());**/
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = objectWriter.writeValueAsString(stocks);
		kafkaTemplate.send(createTopicName, json);
	}

	public void removeStocksEvent(Stocks stock) throws JsonProcessingException {
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = objectWriter.writeValueAsString(stock);
		kafkaTemplate.send(removeTopicName, json);
	}

}