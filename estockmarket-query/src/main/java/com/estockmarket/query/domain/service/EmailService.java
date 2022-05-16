package com.estockmarket.query.domain.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JavaMailSender javaMailSender;

	public void sendOtpMessage(String to, String subject, String message) throws MessagingException {
		LOGGER.info("send otp message to ,subject,message{}", to,subject,message);
		MimeMessage msg = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(msg, true);

		helper.setTo(to);

		helper.setSubject(subject);

		helper.setText(message, true);

		javaMailSender.send(msg);
	}

}