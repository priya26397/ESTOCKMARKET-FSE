package com.estockmarket.query.application.controller;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.estockmarket.query.application.config.JwtTokenUtil;
import com.estockmarket.query.application.dto.UserDTO;
import com.estockmarket.query.domain.exception.UserNotFoundException;
import com.estockmarket.query.domain.service.EmailService;
import com.estockmarket.query.domain.service.EmailTemplate;
import com.estockmarket.query.domain.service.OTPService;
import com.estockmarket.query.domain.service.UserService;
import com.estockmarket.query.domain.util.JwtRequest;
import com.estockmarket.query.domain.util.JwtResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/${api.version}/query/user/")
@Api(value = "user", description = "Operations pertaining to authenticate the user")
public class UserController {
	
	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserService userService;

	@Autowired
	public OTPService otpService;

	@Autowired
	public EmailService emailService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@ApiOperation(value = "Authenticate the user", response = JwtResponse.class)
	@RequestMapping(value = "/authenticate", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest credentials) {
		LOGGER.info("authenticate user {}", credentials);
		UserDTO dto = userService.authenticate(credentials);
		if (dto.getUserName() != null) {
			final String token = jwtTokenUtil.generateToken(dto);
			return ResponseEntity.ok(new JwtResponse(token));
		} else {
			throw new UserNotFoundException();
		}
	}

	@ApiOperation(value = "Generate OTP", response = String.class)
	@RequestMapping(value = "/generateOtp", method = RequestMethod.GET, produces = "text/plain")
	public String generateOTP(@RequestParam(value = "email") String email) throws MessagingException {
		LOGGER.info("generateotp {}", email);
		int otp = otpService.generateOTP(email);
		// Generate The Template to send OTP
		EmailTemplate template = new EmailTemplate("SendOtp.html");
		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("user", email);
		replacements.put("otpnum", String.valueOf(otp));
		System.out.println(replacements);
//		String message = template.getTemplate(replacements);
		String message = String.valueOf(otp);
		emailService.sendOtpMessage(email, "OTP - Authentication", message);

		return "otppage";
	}

	@ApiOperation(value = "Validate OTP", response = String.class)
	@RequestMapping(value = "/validateOtp", method = RequestMethod.GET, produces = "text/plain")
	public @ResponseBody String validateOtp(@RequestParam(value = "email") String email,
			@RequestParam("otpnum") int otpnum) {
		LOGGER.info("validate otp email,otp{}", email,otpnum);
		final String SUCCESS = "Entered Otp is valid";
		final String FAIL = "Entered Otp is NOT valid. Please Retry!";
		// Validate the Otp
		if (otpnum >= 0) {

			int serverOtp = otpService.getOtp(email);
			if (serverOtp > 0) {
				if (otpnum == serverOtp) {
					otpService.clearOTP(email);

					return (SUCCESS);
				} else {
					return FAIL;
				}
			} else {
				return FAIL;
			}
		} else {
			return FAIL;
		}
	}

}
