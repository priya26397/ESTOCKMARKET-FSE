package com.estockmarket.command.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estockmarket.command.domain.entity.User;
import com.estockmarket.command.domain.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;	

@RestController
@RequestMapping("/api/${api.version}/command/user/")
@Api(value="user", description="Operations pertaining to register the user")
public class UserController {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;


	@ApiOperation(value = "Register new user",response = User.class)    
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<User> registerUser(@RequestBody User userDetails) throws JsonProcessingException {
		LOGGER.info("Save user details {}", userDetails);
		return new ResponseEntity<User>(userService.save(userDetails), HttpStatus.OK);
	}

}
