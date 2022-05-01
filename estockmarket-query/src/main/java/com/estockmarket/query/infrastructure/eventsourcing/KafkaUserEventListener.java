package com.estockmarket.query.infrastructure.eventsourcing;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.estockmarket.query.domain.model.User;
import com.estockmarket.query.domain.service.UserService;
import com.google.gson.Gson;

@Component
public class KafkaUserEventListener {

	@Autowired
	private UserService userService;

	@KafkaListener(topics = "${message.topic.createUser}", groupId = "group_id", containerFactory = "userKafkaListenerFactory")
	public void listen(ConsumerRecord<String, String> consumerRecord) throws Exception {
		User user = new Gson().fromJson(consumerRecord.value(), User.class);
		userService.createUser(user);
	}
}