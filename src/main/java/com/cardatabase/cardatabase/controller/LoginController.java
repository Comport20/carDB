package com.cardatabase.cardatabase.controller;

import com.cardatabase.cardatabase.domain.AccountCredentials;
import com.cardatabase.cardatabase.service.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginController(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    @PostMapping("/login")
    public ResponseEntity<?> getToken(@RequestBody AccountCredentials accountCredentials){
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
                accountCredentials.username(), accountCredentials.password());

        Authentication authentication = authenticationManager.authenticate(upat);
        String jwts = jwtService.getToken(authentication.getName());

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + jwts)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,"Authorization")
                .build();
    }
}
