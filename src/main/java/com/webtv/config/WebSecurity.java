package com.webtv.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<String> permitAllEndpointList = Arrays.asList(
            "/v2/api-docs",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/api/v1/**"
        );
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers(permitAllEndpointList.toArray(new String[permitAllEndpointList.size()]))
                .permitAll();
            // .and()
            //     .authorizeRequests()
            //     .anyRequest().authenticated()
            // .and()
            //     .addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
            //     // .addFilterBefore(buildAjaxLoginProcessingFilter(AUTHENTICATION_URL), UsernamePasswordAuthenticationFilter.class)
            //     .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(permitAllEndpointList, "/api/v1/**"), UsernamePasswordAuthenticationFilter.class);

        // http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}