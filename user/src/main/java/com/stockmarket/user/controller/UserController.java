package com.stockmarket.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.user.config.JwtTokenUtil;
import com.stockmarket.user.dto.UserDTO;
import com.stockmarket.user.exception.UserNotFoundException;
import com.stockmarket.user.model.JwtRequest;
import com.stockmarket.user.model.JwtResponse;
import com.stockmarket.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/${api.version}/user/")
@Api(value="user", description="Operations pertaining to register & authenticate the user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@ApiOperation(value = "Register new user",response = UserDTO.class)    
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDetails) {
		return new ResponseEntity<UserDTO>(userService.save(userDetails), HttpStatus.OK);
	}

	@ApiOperation(value = "Authenticate the user",response = JwtResponse.class)
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, produces = "application/json")
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
