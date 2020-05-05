package com.webtv.controller;

import javax.validation.Valid;

import com.webtv.commons.LoginResponse;
import com.webtv.commons.ResponseModel;
import com.webtv.commons.Validator;
import com.webtv.entity.User;
import com.webtv.forms.LoginForm;
import com.webtv.service.LoginService;
import com.webtv.service.endpoints.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "API endpoints for user entity.")
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService uService;

    @Autowired
    private LoginService loginService;

    @ApiOperation("Signup. Create a new user for given email.")
    @PostMapping("signup")
    ResponseModel<User> signup(@Valid User user, BindingResult bResult) {
        return uService.create(user, bResult);
    }

    @ApiOperation("Login. Check an user by password and username. If it was valid, this endpoint return an access token and a refresh token for usage later.")
    @PostMapping("login")
    ResponseModel<LoginResponse> login(@Valid LoginForm form, BindingResult bResult) {
        Validator.checkCreate(bResult);
        return ResponseModel.success(loginService.login(form.getEmail(), form.getPassword()));
    }
}