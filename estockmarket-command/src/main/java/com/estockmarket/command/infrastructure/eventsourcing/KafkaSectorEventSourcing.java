package com.estockmarket.command.infrastructure.eventsourcing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.estockmarket.command.domain.entity.Sector;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Component
public class KafkaSectorEventSourcing {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value(value = "${message.topic.createSector}")
	private String createTopicName;

	@Value(value = "${message.topic.removeSector}")
	private String removeTopicName;

	public void createSectorEvent(Sector sector) throws JsonProcessingException {
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = objectWriter.writeValueAsString(sector);
		LOGGER.info("{} topic send successfully {}",createTopicName, json);
		kafkaTemplate.send(createTopicName, json);
	}

	public void removeSectorEvent(Sector sector) throws JsonProcessingException {
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = objectWriter.writeValueAsString(sector);
		LOGGER.info("{} topic send successfully {}",removeTopicName, json);
		kafkaTemplate.send(removeTopicName, json);
	}

}