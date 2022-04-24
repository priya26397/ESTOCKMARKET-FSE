package com.stockmarket.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.stockmarket.user.dto.UserDTO;
import com.stockmarket.user.exception.DataInvalidException;
import com.stockmarket.user.model.JwtRequest;
import com.stockmarket.user.repo.UserRepository;

@Service
public class UserService{
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserDTO save(UserDTO userDetails) {
		userDetails.setId(sequenceGeneratorService.generateSequence(UserDTO.SEQUENCE_NAME));
		userDetails.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		userDetails.setEnable(true);
		return userRepo.save(userDetails);
	}

	public UserDTO authenticate(JwtRequest credentials) {
		if (credentials.getUsername() == null || credentials.getPassword() == null) {
			throw new DataInvalidException();
		}
		UserDTO dto = userRepo.findByUserName(credentials.getUsername());
		return dto;

	}

}
