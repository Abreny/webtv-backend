package com.webtv.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webtv.commons.LoginResponse;
import com.webtv.commons.ResponseModel;
import com.webtv.config.WebSecurity;
import com.webtv.exception.InvalidAuthorizationHeader;
import com.webtv.exception.RefreshTokenException;
import com.webtv.service.LoginService;
import com.webtv.service.security.extractor.TokenExtractor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "API Endpoints for refresh token.")
@RequestMapping("api/v1/auth")
@RestController
public class AuthController {

    @Autowired
    private TokenExtractor tokenExtractor;

    @Autowired
    private LoginService loginService;

    @ApiOperation("RefreshToken. Refresh a token by a given token payload. It serve as generating new access token when it's was expired.")
    @GetMapping(value="token", produces={ MediaType.APPLICATION_JSON_VALUE })
    public ResponseModel<LoginResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurity.AUTHENTICATION_HEADER_NAME));
            return ResponseModel.success(loginService.refreshToken(tokenPayload));
        } catch (InvalidAuthorizationHeader e) {
            throw new RefreshTokenException(e);
        }
    }
}