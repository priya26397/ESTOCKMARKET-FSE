package com.estockmarket.query.infrastructure.eventsourcing;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.estockmarket.query.domain.model.Company;
import com.estockmarket.query.domain.service.CompanyService;
import com.google.gson.Gson;

@Component
public class KafkaCompanyEventListener {

	@Autowired
	private CompanyService companyService;

	@KafkaListener(topics = "${message.topic.createCompany}", groupId = "group_id", containerFactory = "userKafkaListenerFactory")
	public void listen(String consumerRecord) throws Exception {
		Company company = new Gson().fromJson(consumerRecord, Company.class);
		companyService.createCompany(company);
	}

	@KafkaListener(topics = "${message.topic.removeCompany}", groupId = "group_id", containerFactory = "userKafkaListenerFactory")
	public void removeCompany(String consumerRecord) throws Exception {
		Company company = new Gson().fromJson(consumerRecord, Company.class);
		companyService.removeCompany(company.getId());
	}
}