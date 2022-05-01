package com.estockmarket.query.domain.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estockmarket.query.application.dto.UserDTO;
import com.estockmarket.query.domain.exception.DataInvalidException;
import com.estockmarket.query.domain.model.User;
import com.estockmarket.query.domain.util.JwtRequest;
import com.estockmarket.query.infrastructure.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	UserRepository userRepo;

	public UserDTO authenticate(JwtRequest credentials) {
		if (credentials.getUsername() == null || credentials.getPassword() == null) {
			throw new DataInvalidException();
		}
		Optional<User> user = userRepo.findByUserName(credentials.getUsername());
		return (UserDTO) modelMapper.map(user.get(), UserDTO.class);

	}

	public void createUser(User user) {
		userRepo.save(user);		
	}

}
