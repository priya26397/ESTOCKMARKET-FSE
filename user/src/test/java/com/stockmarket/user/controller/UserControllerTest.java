package com.stockmarket.user.controller;

import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.stockmarket.user.config.JwtTokenUtil;
import com.stockmarket.user.dto.UserDTO;
import com.stockmarket.user.model.JwtRequest;
import com.stockmarket.user.service.UserService;


@SpringBootTest
public class UserControllerTest {

	@InjectMocks
	private UserController userController;
	
	@Mock
	private UserService userService;
	
	@Mock
	private JwtTokenUtil jwtTokenUtil;
	
	@Test
	public void registerUserTest() {
		UserDTO userDetails=new UserDTO();
		userDetails.setUserName("xyz");
		userDetails.setPassword("123ar");
		Mockito.when(userService.save(userDetails)).thenReturn(userDetails);
		userController.registerUser(userDetails);
	}
	
	@Test
	public void createAuthenticationTokenTest() {
		UserDTO userDto=new UserDTO();
		userDto.setUserName("xyz");
		userDto.setPassword("123ar");
		JwtRequest jwtRequest=new JwtRequest();
		Mockito.when(userService.authenticate(jwtRequest)).thenReturn(userDto);
		Mockito.when(jwtTokenUtil.generateToken(userDto)).thenReturn(anyString());
		userController.createAuthenticationToken(jwtRequest);
	}
}
