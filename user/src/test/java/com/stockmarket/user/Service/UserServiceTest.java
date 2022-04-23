package com.stockmarket.user.Service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.stockmarket.user.dto.UserDTO;
import com.stockmarket.user.model.JwtRequest;
import com.stockmarket.user.repo.UserRepository;
import com.stockmarket.user.service.SequenceGeneratorService;
import com.stockmarket.user.service.UserService;

@SpringBootTest
public class UserServiceTest {
	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private SequenceGeneratorService sequenceGeneratorService;
	
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Test
	public void save() {
		UserDTO userDetails=new UserDTO();
		userDetails.setUserName("xyz");
		userDetails.setPassword("123ar");
		when(sequenceGeneratorService.generateSequence(anyString())).thenReturn((long) 1001);
		when(bCryptPasswordEncoder.encode(userDetails.getPassword())).thenReturn(anyString());
		when(userRepository.save(userDetails)).thenReturn(userDetails);
		userService.save(userDetails);
	}
	
	@Test
	public void authenticateInvalidTest() {
		JwtRequest jwtRequest=new JwtRequest();
		userService.authenticate(jwtRequest);
		
	}
	
	@Test
	public void authenticateValidTest() {
		UserDTO userDetails=new UserDTO();
		userDetails.setUserName("xyz");
		userDetails.setPassword("123ar");
		JwtRequest jwtRequest=new JwtRequest();
		jwtRequest.setUsername("xyz");
		jwtRequest.setPassword("123ar");
		when(userRepository.findByUserName(jwtRequest.getUsername())).thenReturn(userDetails);
		userService.authenticate(jwtRequest);
		
	}
}
