package com.backend.BiteBox.controller;

import com.backend.BiteBox.io.UserRequest;
import com.backend.BiteBox.io.UserResponse;
import com.backend.BiteBox.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody UserRequest request) {
        UserResponse response = userService.registerUser(request);
        return response;
    }

}
