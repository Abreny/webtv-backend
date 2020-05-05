package com.webtv.service.security;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.webtv.config.JWTSettings;
import com.webtv.exception.InvalidToken;
import com.webtv.exception.JWTExpiredTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JWTTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    private static Logger logger = LoggerFactory.getLogger(JWTTokenUtil.class);

    @Autowired
    private JWTTokenFactory tokenFactory;

    @Autowired
    private JWTSettings jwtSettings;

    // retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSettings.getTokenSigningKey()).parseClaimsJws(token).getBody();
    }

    public Jws<Claims> parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtSettings.getTokenSigningKey()).parseClaimsJws(token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            logger.error("Invalid JWT Token", ex);
            throw new InvalidToken("Invalid JWT token");
        } catch (ExpiredJwtException expiredEx) {
            logger.info("JWT Token is expired", expiredEx);
            throw new JWTExpiredTokenException(token, "JWT Token expired", expiredEx);
        }
    }

    // check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // generate token for user
    public String generateToken(UserDetails userDetails) {
        return this.tokenFactory.createAccessJwtToken(userDetails);
    }

    // generate token for user
    public String generateRefreshToken(UserDetails userDetails) {
        return this.tokenFactory.createRefreshToken(userDetails);
    }

    // validate token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)) && hasAccess(token, "ACCESS_TOKEN");
    }

    @SuppressWarnings("unchecked")
    public boolean hasAccess(String token, String access) {
        List<String> scopes = getAllClaimsFromToken(token).get("scopes", List.class);
        if (scopes == null || scopes.isEmpty() 
                || !scopes.stream().filter(scope -> String.format("ROLE_%s", access).equals(scope)).findFirst().isPresent()) {
            return false;
        }
        return true;
    }
}