package com.webtv.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    ResponseModel<LoginResponse> signup(@Valid User user, BindingResult bResult) {
        final User u = uService.create(user, bResult).getData();
        return ResponseModel.created(loginService.tokenOf(u));
    }

    @ApiOperation("Login. Check an user by password and username. If it was valid, this endpoint return an access token and a refresh token for usage later.")
    @PostMapping("login")
    ResponseModel<LoginResponse> login(@Valid LoginForm form, BindingResult bResult) {
        Validator.checkCreate(bResult);
        return ResponseModel.success(loginService.login(form.getEmail(), form.getPassword()));
    }

    @ApiOperation("User. Get the list of all users.")
    @GetMapping
    ResponseModel<List<User>> users() {
        return uService.list();
    }

    @ApiOperation("UpdateProfile. Update the profile of the user.")
    @PutMapping("{userId:[0-9]}")
    ResponseModel<User> update(@Valid User user, BindingResult bResult, @PathVariable("userId") Long userId) {
        return this.uService.update(user, bResult, userId);
    }

    @ApiOperation("Role. Change the role of a given user.")
    @PutMapping("role/{userId:[0-9]+}/{roleId:1|2}")
    ResponseModel<LoginResponse> login(@PathVariable("userId") Long userId, @PathVariable("roleId") int role) {
        return ResponseModel.success(loginService.tokenOf(uService.changeRole(userId, role).getData()));
    }
}