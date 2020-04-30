package com.webtv.controller;

import javax.validation.Valid;

import com.webtv.commons.ResponseModel;
import com.webtv.entity.User;
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

    @ApiOperation("Signup. Create a new user for given email.")
    @PostMapping("signup")
    ResponseModel<User> signup(@Valid User user, BindingResult bResult) {
        return uService.create(user, bResult);
    }
}