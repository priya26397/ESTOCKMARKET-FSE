package com.estockmarket.command.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.estockmarket.command.domain.entity.User;
import com.estockmarket.command.domain.exception.UserAlreadyExistsException;
import com.estockmarket.command.infrastructure.eventsourcing.KafkaUserEventSourcing;
import com.estockmarket.command.infrastructure.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private KafkaUserEventSourcing kafkaUserEventSourcing;

	public User save(User userDetails) throws JsonProcessingException {
		Optional<User> user = userRepo.findByEmail(userDetails.getEmail());
		if (user.isPresent()) {
			throw new UserAlreadyExistsException();
		}
		userDetails.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		userDetails.setEnable(true);
		User userResult=userRepo.save(userDetails);
		kafkaUserEventSourcing.createUser(userResult);
		return 	userResult;
				}
}
