package com.webtv.service.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webtv.commons.ResponseModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

public class JWTAccessDeniedHandler implements AccessDeniedHandler {
 
    public static final Logger LOG
      = LoggerFactory.getLogger(JWTAccessDeniedHandler.class);
 
    @Override
    public void handle(
      HttpServletRequest request,
      HttpServletResponse response, 
      AccessDeniedException exc) throws IOException, ServletException {
         
        Authentication auth 
          = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            LOG.warn(String.format("User: %s, with role: %s, attempted to access the protected URL: %s", auth.getName(), auth.getAuthorities(), request.getRequestURI()));
        }
 
        final ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(), ResponseModel.forbidden(exc.getMessage()));
    }
}