package com.backend.BiteBox.controller;

import com.backend.BiteBox.io.AuthenticationRequest;
import com.backend.BiteBox.io.AuthenticationResponse;
import com.backend.BiteBox.service.AppUserDetailsService;
import com.backend.BiteBox.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);

        return new AuthenticationResponse(request.getEmail(), jwtToken);
    }

}
