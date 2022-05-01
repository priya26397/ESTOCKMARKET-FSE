package com.estockmarket.gateway.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtFilter extends OncePerRequestFilter {

	private static Logger LOG = LoggerFactory.getLogger(JwtFilter.class);
	private static final String IGNORE_URL[] = { "/api/v1.0/query/","/api/v1.0/command/user", "/actuator/" };

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		LOG.info("Validation JWT token");

		HttpServletRequest req = (HttpServletRequest) request;
		String authorization = req.getHeader("Authorization");

		if (authorization != null && authorization.startsWith("Bearer ")) {
			String token = authorization.substring(7);
			Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
			req.setAttribute("claims", claims);
			LOG.info("Validated successfully");
			filterChain.doFilter(request, response);
		}

	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();
		LOG.info("Should not filter {}", path);
		return checkPathPattern(path);
	}

	boolean checkPathPattern(String path) {
		for (String each : IGNORE_URL) {
			if (path.contains(each)) {
				return true;
			}
		}
		return false;
	}
}