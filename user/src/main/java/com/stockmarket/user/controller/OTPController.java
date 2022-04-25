package com.stockmarket.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.user.service.EmailService;
import com.stockmarket.user.service.EmailTemplate;
import com.stockmarket.user.service.OTPService;

@RestController
@RequestMapping("/api/${api.version}/user/")
public class OTPController {

	@Autowired
	public OTPService otpService;

	@Autowired
	public EmailService emailService;

	@GetMapping("/generateOtp")
	public String generateOTP(@RequestParam(value = "email") String email) throws MessagingException {

		int otp = otpService.generateOTP(email);
		// Generate The Template to send OTP
		EmailTemplate template = new EmailTemplate("SendOtp.html");
		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("user", email);
		replacements.put("otpnum", String.valueOf(otp));
		System.out.println(replacements);
		String message = template.getTemplate(replacements);
		emailService.sendOtpMessage(email, "OTP - Authentication", message);

		return "otppage";
	}

	@RequestMapping(value = "/validateOtp", method = RequestMethod.GET)
	public @ResponseBody String validateOtp(@RequestParam(value = "email") String email, @RequestParam("otpnum") int otpnum) {

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