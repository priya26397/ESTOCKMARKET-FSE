package com.stockmarket.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.user.config.JwtTokenUtil;
import com.stockmarket.user.dto.UserDTO;
import com.stockmarket.user.exception.UserNotFoundException;
import com.stockmarket.user.model.JwtRequest;
import com.stockmarket.user.model.JwtResponse;
import com.stockmarket.user.service.UserService;

@RestController
@RequestMapping("/api/${api.version}/user/")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDetails) {
		return new ResponseEntity<UserDTO>(userService.save(userDetails), HttpStatus.OK);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest credentials) {
		UserDTO dto = userService.authenticate(credentials);
		if (dto != null) {
			final String token = jwtTokenUtil.generateToken(dto);
			return ResponseEntity.ok(new JwtResponse(token));
		} else {
			throw new UserNotFoundException();
		}
	}

}
