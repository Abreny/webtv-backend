package com.webtv.service.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.webtv.commons.ResponseModel;
import com.webtv.commons.UnauthorizedResponse;
import com.webtv.exception.AuthMethodNotSupportedException;
import com.webtv.exception.InvalidAuthorizationHeader;
import com.webtv.exception.InvalidToken;
import com.webtv.exception.JWTExpiredTokenException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author abned.fandresena
 *
 * Apr 26, 2020
 */
@Component
public class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
	private final ObjectMapper mapper;

	@Autowired
	public JWTAuthenticationFailureHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }	
    
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException {
		
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		UnauthorizedResponse resp = UnauthorizedResponse.of("AUTHENTICATION_REQUIRED", "Authentication failed");
		if (e instanceof BadCredentialsException) {
			resp = UnauthorizedResponse.of("BAD_LOGIN", "Invalid username or password");
		} else if (e instanceof JWTExpiredTokenException) {
			resp = UnauthorizedResponse.of("BAD_TOKEN", "Token has expired");
		} else if (e instanceof AuthMethodNotSupportedException ) {
			resp = UnauthorizedResponse.of("BAD_TOKEN", e.getMessage());
		} else if (e instanceof InvalidAuthorizationHeader) {
		    resp = UnauthorizedResponse.of("BAD_TOKEN", e.getMessage());
		} else if (e instanceof InvalidToken) {
		    resp = UnauthorizedResponse.of("BAD_TOKEN", e.getMessage());
		}

		mapper.writeValue(response.getWriter(), ResponseModel.unauthorized(resp));
	}
}
