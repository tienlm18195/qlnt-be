package com.tainika.qlnt.qlnt.controller;

import com.tainika.qlnt.qlnt.dto.auth.AuthRequest;
import com.tainika.qlnt.qlnt.dto.auth.AuthResponse;
import com.tainika.qlnt.qlnt.model.UserLoginDetails;
import com.tainika.qlnt.qlnt.service.UserLoginDetailsService;
import com.tainika.qlnt.qlnt.ultil.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserLoginDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticate(@RequestBody AuthRequest authRequest) throws Exception {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping("/valid_token")
    public ResponseEntity<String> validToken(@RequestBody AuthRequest auth) {
        UserLoginDetails loginUser = (UserLoginDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
        if (!loginUser.getUsername().equals(auth.getUsername())) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        return ResponseEntity.ok(loginUser.getUsername());
    }
}
