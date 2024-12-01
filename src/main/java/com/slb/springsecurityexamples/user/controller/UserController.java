package com.slb.springsecurityexamples.user.controller;

import com.slb.springsecurityexamples.user.dto.RegisterRequest;
import com.slb.springsecurityexamples.user.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final RegisterService registerService;

    @Autowired
    public UserController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(RegisterRequest registerRequest){
        registerService.register(registerRequest);
        return ResponseEntity.ok().build();
    }
}