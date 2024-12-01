    package com.slb.springsecurityexamples.user.exception;

    import org.springframework.http.HttpStatus;
    import org.springframework.web.bind.annotation.ResponseStatus;

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "이미 존재하는 사용자 이름입니다.")
    public class UsernameAlreadyExistsException extends RuntimeException {

        public UsernameAlreadyExistsException() {
            super("이미 존재하는 사용자 이름입니다.");
        }
    }