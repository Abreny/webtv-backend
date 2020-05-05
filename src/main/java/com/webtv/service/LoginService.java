package com.webtv.service;

import com.webtv.commons.LoginResponse;
import com.webtv.entity.User;
import com.webtv.exception.BadLoginException;
import com.webtv.service.security.JWTTokenUtil;
import com.webtv.service.security.UserWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginService implements LoginInterface {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired                                                                                                          
    private JWTTokenUtil jwtTokenUtil;
    
    public LoginResponse login(String username, String password) {                                                                                   
        try {                                                                                                                                       
            final Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            final UserWrapper uw = (UserWrapper)auth.getPrincipal();
            return new LoginResponse(uw.getUser(), jwtTokenUtil.generateToken(uw), jwtTokenUtil.generateRefreshToken(uw));
        } catch (DisabledException e) {
            throw new BadLoginException("Account not avalaible or not activated");
        } catch (BadCredentialsException e) {                                                                                                                                                                                   
            throw new BadLoginException("username or password invalid");
        }
    }
}