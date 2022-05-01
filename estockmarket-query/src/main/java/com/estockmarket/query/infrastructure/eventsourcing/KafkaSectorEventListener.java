package com.estockmarket.query.infrastructure.eventsourcing;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.estockmarket.query.domain.model.Sector;
import com.estockmarket.query.domain.service.SectorService;
import com.google.gson.Gson;

@Component
public class KafkaSectorEventListener {

	@Autowired
	private SectorService sectorService;

	@KafkaListener(topics = "${message.topic.createSector}", groupId = "group_id", containerFactory = "userKafkaListenerFactory")
	public void sectorCreate(String consumerRecord) throws Exception {
		Sector sector = new Gson().fromJson(consumerRecord, Sector.class);
		sectorService.createSector(sector);
	}
	
	@KafkaListener(topics = "${message.topic.removeSector}", groupId = "group_id", containerFactory = "userKafkaListenerFactory")
	public void sectorDelete(String consumerRecord) throws Exception {
		Sector sector = new Gson().fromJson(consumerRecord, Sector.class);
		sectorService.removeSector(sector.getId());
	}
}