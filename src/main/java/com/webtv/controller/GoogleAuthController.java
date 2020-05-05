package com.webtv.controller;

import com.webtv.commons.GoogleCredential;
import com.webtv.commons.ResponseModel;
import com.webtv.service.youtube.GoogleAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth/google")
public class GoogleAuthController {

    @Autowired
    private GoogleAuth auth;

    @GetMapping("authorize-url")
    ResponseModel<String> authorizeUrl() {
        return ResponseModel.success(auth.authorizeUrl().build());
    }

    @GetMapping("authorized-url")
    ResponseModel<GoogleCredential> onAuthorizeUrl(@RequestParam(name = "code", required = false) String code) {
        if(code != null) {
            return ResponseModel.success(GoogleCredential.of(auth.forNewCode(code)));
        }
        return ResponseModel.success(GoogleCredential.of(auth.authorize("fanabned@gmail.com")));
    }
}