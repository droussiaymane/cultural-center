package com.ju.islamicculturalcenter.restcontrollers.auth;

import com.ju.islamicculturalcenter.dto.auth.AuthenticationResponse;
import com.ju.islamicculturalcenter.dto.auth.CreateAuthenticationRequest;
import com.ju.islamicculturalcenter.dto.auth.LogoutRequest;
import com.ju.islamicculturalcenter.dto.response.CODE;
import com.ju.islamicculturalcenter.dto.response.Response;
import com.ju.islamicculturalcenter.service.auth.UserAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/auth")
public class UserAuthentication {

    private final UserAuthService userAuthService;

    public UserAuthentication(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<Response<AuthenticationResponse>> login(@RequestBody CreateAuthenticationRequest authenticationRequest) {
        return new ResponseEntity<>(Response.<AuthenticationResponse>builder()
                .data(userAuthService.login(authenticationRequest))
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build(), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Response<?>> logout(@RequestBody LogoutRequest logoutRequest) {
        userAuthService.logout(logoutRequest);
        return new ResponseEntity<>(Response.builder()
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build(),
                HttpStatus.OK);
    }
}
