package com.ju.islamicculturalcenter.service.auth;

import com.ju.islamicculturalcenter.dto.auth.CustomUserDetails;
import com.ju.islamicculturalcenter.entity.UserEntity;
import com.ju.islamicculturalcenter.exceptions.CustomBadCredentialsException;
import com.ju.islamicculturalcenter.exceptions.UserNotFoundException;
import com.ju.islamicculturalcenter.repos.UserRepo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return buildCustomUserDetailsOfUsername(s);
    }

    private CustomUserDetails buildCustomUserDetailsOfUsername(String username) {
        UserEntity user = userRepo.findByIsActiveAndEmail(true, username)
                .orElseThrow(() -> new CustomBadCredentialsException("Incorrect Username"));

        return CustomUserDetails.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUserName())
                .name(user.getUserName())
                .isEnabled(user.getIsActive())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().getTitle())
                .password(user.getPassword())
                .roleGroup(user.getRole().getGroups().name())
                .build();
    }
}
