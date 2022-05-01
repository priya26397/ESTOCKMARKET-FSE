package com.estockmarket.command.infrastructure.eventsourcing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.estockmarket.command.domain.entity.Company;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Component
public class KafkaCompanyEventSourcing {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value(value = "${message.topic.createCompany}")
	private String createTopicName;

	@Value(value = "${message.topic.removeCompany}")
	private String removeTopicName;

	public void createCompanyEvent(Company company) throws JsonProcessingException {
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = objectWriter.writeValueAsString(company);
		kafkaTemplate.send(createTopicName, json);
	}

	public void removeCompanyEvent(Company company) throws JsonProcessingException {
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = objectWriter.writeValueAsString(company);
		kafkaTemplate.send(removeTopicName, json);
	}

}