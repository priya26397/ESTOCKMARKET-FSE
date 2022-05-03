package com.estockmarket.command.infrastructure.eventsourcing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.estockmarket.command.domain.entity.Stocks;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Component
public class KafkaStocksEventSourcing {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value(value = "${message.topic.createStock}")
	private String createTopicName;
	
	@Value(value = "${message.topic.updateStock}")
	private String updateTopicName;

	@Value(value = "${message.topic.removeStock}")
	private String removeTopicName;

	public void createStocksEvent(Stocks stocks) throws JsonProcessingException {
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = objectWriter.writeValueAsString(stocks);
		LOGGER.info("{} topic send successfully {}",createTopicName, json);
		kafkaTemplate.send(createTopicName, json);
	}
	
	public void updateStocksEvent(Stocks stocks) throws JsonProcessingException {
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = objectWriter.writeValueAsString(stocks);
		LOGGER.info("{} topic send successfully {}",updateTopicName, json);
		kafkaTemplate.send(updateTopicName, json);
	}

	public void removeStocksEvent(Stocks stock) throws JsonProcessingException {
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = objectWriter.writeValueAsString(stock);
		LOGGER.info("{} topic send successfully {}",removeTopicName, json);
		kafkaTemplate.send(removeTopicName, json);
	}

}