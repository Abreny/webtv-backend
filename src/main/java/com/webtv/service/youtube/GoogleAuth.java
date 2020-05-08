package com.webtv.service.youtube;

import java.io.IOException;
import java.security.SecureRandom;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.webtv.exception.ServerError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GoogleAuth {

    @Autowired
    @Qualifier("googleCodeFlow")
    private GoogleAuthorizationCodeFlow flow;

    @Value("${app.google.auth.callback_url}")
    private String callbackUrl;

    private String token;

    /**
     * Authorizes the installed application to access user's protected data.
     *
     * @param code The userId of an user to load a credential
     */
    public Credential authorize(String email) {
        try {
            return flow.loadCredential(email);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServerError(e);
        }
    }

    public GoogleAuthorizationCodeRequestUrl authorizeUrl() {
        token = String.format("google;%d", new SecureRandom().nextInt());
        return flow.newAuthorizationUrl().setState(token).setRedirectUri(callbackUrl);
    }

    public Credential forNewCode(String code) {
        try {
            TokenResponse tokenResponse = flow.newTokenRequest(code)
                .setRedirectUri(callbackUrl)
                .execute();
            return flow.createAndStoreCredential(tokenResponse, "fanabned@gmail.com");
        } catch (IOException e) {
            throw new ServerError(e);
        }
    }
    public void deleteToken(String code) {
        try {
            flow.getCredentialDataStore().delete(code);
        } catch (IOException e) {
            throw new ServerError(e);
        }
    }
    public void deleteToken() {
        try {
            flow.getCredentialDataStore().clear();
        } catch (IOException e) {
            throw new ServerError(e);
        }
    }
}