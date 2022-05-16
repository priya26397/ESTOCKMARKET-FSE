package com.estockmarket.query.domain.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

@Service
public class OTPService {
	
	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private static final Integer EXPIRE_MINS = 4;
	private LoadingCache<String, Integer> otpCache;

	public OTPService() {
		super();
		LOGGER.info("inside otp servcie {}");
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Integer>() {
					public Integer load(String key) {
						return 0;
					}
				});
	}

	public int generateOTP(String key) {
		LOGGER.info("generate otp {}", key);
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		otpCache.put(key, otp);
		return otp;
	}

	public int getOtp(String key) {
		LOGGER.info("get otp {}", key);
		try {
			return otpCache.get(key);
		} catch (Exception e) {
			return 0;
		}
	}

	public void clearOTP(String key) {
		LOGGER.info("clear otp {}", key);
		otpCache.invalidate(key);
	}
}