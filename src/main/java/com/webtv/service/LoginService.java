package com.webtv.service;

import com.webtv.commons.LoginResponse;
import com.webtv.entity.User;
import com.webtv.exception.BadLoginException;
import com.webtv.exception.InvalidToken;
import com.webtv.exception.JWTExpiredTokenException;
import com.webtv.repository.UserRepository;
import com.webtv.service.security.JWTTokenUtil;
import com.webtv.service.security.UserWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service("loginService")
public class LoginService implements LoginInterface {

    private static Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired                                                                                                          
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userService;

    private String username;
    
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

    public LoginResponse refreshToken(String tokenPayload) {
        try {
            username = jwtTokenUtil.getUsernameFromToken(tokenPayload);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            logger.error("Invalid JWT Token", ex);
            throw new InvalidToken("Invalid JWT token");
        } catch (ExpiredJwtException expiredEx) {
            logger.info("JWT Token is expired", expiredEx);
            throw new JWTExpiredTokenException(tokenPayload, "JWT Token expired", expiredEx);
        }
        
        final User user = userService.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        final UserWrapper userWrapper = new UserWrapper(user);
        return new LoginResponse(user, jwtTokenUtil.generateToken(userWrapper), jwtTokenUtil.generateRefreshToken(userWrapper));
    }
}