package com.webtv.config;

import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.common.collect.Lists;
import com.webtv.service.youtube.GoogleAuthHelper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class AppConfig {

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(-1);
        return multipartResolver;
    }

    @Bean(name = "googleCodeFlow")
    public GoogleAuthorizationCodeFlow codeFlow() {
        final List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.upload");
        final String credentialStore = "uploadvideo";
        return GoogleAuthHelper.codeFlow(scopes, credentialStore);
    }
}