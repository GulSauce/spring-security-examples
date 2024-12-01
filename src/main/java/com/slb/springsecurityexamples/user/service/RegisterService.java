package com.slb.springsecurityexamples.user.service;

import com.slb.springsecurityexamples.user.dto.RegisterRequest;
import com.slb.springsecurityexamples.user.entity.UserEntity;
import com.slb.springsecurityexamples.user.exception.UsernameAlreadyExistsException;
import com.slb.springsecurityexamples.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest registerRequest){
        // 1. username 중복 확인
        boolean exists = userRepository.findByUsername(registerRequest.getUsername()).isPresent();
        if (exists){
            throw new UsernameAlreadyExistsException();
        }

        // 2. 사용자 저장
        UserEntity userEntity = new UserEntity(
                registerRequest.getUsername(),
                passwordEncoder.encode(registerRequest.getPassword())
        );
        userRepository.save(userEntity);
    }


}
