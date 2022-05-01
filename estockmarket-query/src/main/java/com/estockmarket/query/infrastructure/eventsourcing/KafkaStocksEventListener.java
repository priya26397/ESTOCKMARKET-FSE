package com.estockmarket.query.infrastructure.eventsourcing;

import java.time.LocalDate;
import java.util.Date;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.estockmarket.query.domain.model.Stocks;
import com.estockmarket.query.domain.service.StockService;
import com.google.gson.Gson;

@Component
public class KafkaStocksEventListener {

	@Autowired
	private StockService stockService;

	@KafkaListener(topics = "${message.topic.createStock}", groupId = "group_id", containerFactory = "userKafkaListenerFactory")
	public void createStock(String consumerRecord) throws Exception {
		Stocks stocks = new Gson().fromJson(consumerRecord, Stocks.class);
		/**LocalDate created = LocalDate.parse(stocks.getCreatedOn().toString());
		LocalDate updated = LocalDate.parse(stocks.getUpdatedOn().toString());

		stocks.setCreatedOn(created);
	stocks.setCreatedOn(updated);**/

		stockService.createStock(stocks);
	}

	@KafkaListener(topics = "${message.topic.updateStock}", groupId = "group_id", containerFactory = "userKafkaListenerFactory")
	public void updateStock(String consumerRecord) throws Exception {
		Stocks stocks = new Gson().fromJson(consumerRecord, Stocks.class);
		stockService.updateStock(stocks);
	}

	@KafkaListener(topics = "${message.topic.removeStock}", groupId = "group_id", containerFactory = "userKafkaListenerFactory")
	public void removeStock(String consumerRecord) throws Exception {
		Stocks stocks = new Gson().fromJson(consumerRecord, Stocks.class);
		stockService.removeStock(stocks.getId());
	}
}