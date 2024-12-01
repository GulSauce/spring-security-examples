package com.slb.springsecurityexamples.user.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException() {
        super("이미 존재하는 사용자 이름입니다.");
    }
}