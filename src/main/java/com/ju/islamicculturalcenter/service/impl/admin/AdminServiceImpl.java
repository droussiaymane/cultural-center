package com.ju.islamicculturalcenter.service.impl.admin;

import com.ju.islamicculturalcenter.dto.request.admin.admin.AdminRequestDto;
import com.ju.islamicculturalcenter.dto.request.admin.admin.AdminResetPasswordRequestDto;
import com.ju.islamicculturalcenter.dto.request.admin.admin.AdminUpdatePasswordRequestDto;
import com.ju.islamicculturalcenter.dto.request.admin.admin.AdminUpdateRequestDto;
import com.ju.islamicculturalcenter.dto.response.admin.admin.AdminResponseDto;
import com.ju.islamicculturalcenter.entity.AdminEntity;
import com.ju.islamicculturalcenter.entity.UserEntity;
import com.ju.islamicculturalcenter.entity.enums.Group;
import com.ju.islamicculturalcenter.exceptions.NotFoundException;
import com.ju.islamicculturalcenter.exceptions.ValidationException;
import com.ju.islamicculturalcenter.mappers.admin.AdminMapper;
import com.ju.islamicculturalcenter.repos.AdminRepo;
import com.ju.islamicculturalcenter.repos.UserRepo;
import com.ju.islamicculturalcenter.repos.UserRoleRepo;
import com.ju.islamicculturalcenter.service.BaseServiceImpl;
import com.ju.islamicculturalcenter.service.auth.UserDetailsUtil;
import com.ju.islamicculturalcenter.service.helper.CompositeValidator;
import com.ju.islamicculturalcenter.service.helper.EmailHelper;
import com.ju.islamicculturalcenter.service.helper.PasswordHelper;
import com.ju.islamicculturalcenter.service.iservice.MailService;
import com.ju.islamicculturalcenter.service.iservice.admin.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
public class AdminServiceImpl extends BaseServiceImpl<AdminEntity, AdminRequestDto, AdminResponseDto, AdminUpdateRequestDto> implements AdminService {

    private final AdminRepo adminRepo;
    private final AdminMapper adminMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRoleRepo userRoleRepo;
    private final UserRepo userRepo;
    private final MailService mailService;

    public AdminServiceImpl(AdminRepo adminRepo, UserRoleRepo userRoleRepo, UserRepo userRepo, MailService mailService) {
        this.adminRepo = adminRepo;
        this.userRoleRepo = userRoleRepo;
        this.userRepo = userRepo;
        this.mailService = mailService;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.adminMapper = new AdminMapper(userRoleRepo);
    }

    @Override
    public void resetPassword(AdminResetPasswordRequestDto requestDto) { //DONE

        List<String> violations = new CompositeValidator<AdminResetPasswordRequestDto, String>()
                .addValidator(r -> nonNull(r.getId()), "ID cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getNewPassword()), "newPassword cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getConfirmPassword()), "confirmPassword cannot be null")
                .addValidator(r -> isNull(r.getNewPassword()) || isNull(r.getConfirmPassword()) || r.getNewPassword().equals(r.getConfirmPassword()), "password does not match")
                .addValidator(r -> isNull(r.getNewPassword()) || PasswordHelper.validatePassword(r.getNewPassword()), "Password Should be at least 8 character with 1 uppercase")
                .validate(requestDto);
        validate(violations);

        AdminEntity admin = adminRepo.findByIdAndIsActive(requestDto.getId(), true)
                .orElseThrow(() -> new NotFoundException("No Admin Found with ID: " + requestDto.getId()));

        admin.getUser().setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        admin.getUser().setUpdateDate(new Timestamp(System.currentTimeMillis()));
        admin.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        adminRepo.save(admin);

        mailService.sendPasswordEmail(admin.getUser().getFirstName(), admin.getUser().getEmail(), requestDto.getNewPassword());
    }

    @Override
    public void updatePassword(AdminUpdatePasswordRequestDto requestDto) { //DONE

        List<String> violations = new CompositeValidator<AdminUpdatePasswordRequestDto, String>()
                .addValidator(r -> CompositeValidator.hasValue(r.getOldPassword()), "oldPassword cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getNewPassword()), "newPassword cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getConfirmPassword()), "confirmPassword cannot be null")
                .addValidator(r -> isNull(r.getNewPassword()) || isNull(r.getConfirmPassword()) || r.getNewPassword().equals(r.getConfirmPassword()), "password does not match")
                .addValidator(r -> isNull(r.getNewPassword()) || PasswordHelper.validatePassword(r.getNewPassword()), "Password Should be at least 8 character with 1 uppercase")
                .validate(requestDto);
        validate(violations);

        AdminEntity admin = adminRepo.findOne(Example.of(AdminEntity.builder()
                        .active(true)
                        .user(UserEntity.builder().active(true).id(UserDetailsUtil.userDetails().getId()).build())
                        .build()))
                .orElseThrow(() -> new NotFoundException("No Admin Found with this session and user ID: " + UserDetailsUtil.userDetails().getId()));

        if (!passwordEncoder.matches(requestDto.getOldPassword(), admin.getUser().getPassword()))
            throw new ValidationException("old password does not match");

        admin.getUser().setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        admin.getUser().setUpdateDate(new Timestamp(System.currentTimeMillis()));
        admin.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        adminRepo.save(admin);
    }

    @Override
    public AdminResponseDto updateOwnProfile(AdminUpdateRequestDto requestDto) { //DONE

        List<String> violations = new CompositeValidator<AdminUpdateRequestDto, String>()
                .addValidator(r -> CompositeValidator.hasValue(r.getFirstName()), "firstName cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getLastName()), "lastName cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getPhoneNumber()), "phoneNumber cannot be null")
                .validate(requestDto);
        validate(violations);

        AdminEntity entity = adminRepo.findOne(Example.of(AdminEntity.builder()
                                .active(true)
                                .user(UserEntity.builder().active(true).id(UserDetailsUtil.userDetails().getId()).build())
                                .build()))
                .orElseThrow(() -> new NotFoundException("No Admin Found with this session and user ID: " + UserDetailsUtil.userDetails().getId()));

        entity.getUser().setFirstName(isNull(requestDto.getFirstName()) ? entity.getUser().getFirstName() : requestDto.getFirstName());
        entity.getUser().setLastName(isNull(requestDto.getLastName()) ? entity.getUser().getLastName() : requestDto.getLastName());
        entity.getUser().setFacebookUrl(isNull(requestDto.getFacebookUrl()) ? entity.getUser().getFacebookUrl() : requestDto.getFacebookUrl());
        entity.getUser().setPhoneNumber(isNull(requestDto.getPhoneNumber()) ? entity.getUser().getPhoneNumber() : requestDto.getPhoneNumber());
        entity.setIban(isNull(requestDto.getIban()) ? entity.getIban() : requestDto.getIban());
        entity.setAddress(isNull(requestDto.getAddress()) ? entity.getAddress() : requestDto.getAddress());

        entity.getUser().setUpdateDate(new Timestamp(System.currentTimeMillis()));
        entity.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        return adminMapper.mapEntityToDto(adminRepo.save(entity));
    }

    @Override
    public List<AdminResponseDto> searchAdminByName(String name) {

        List<String> violations = new CompositeValidator<String, String>()
                .addValidator(CompositeValidator::hasValue, "keyword cannot be empty")
                .addValidator(r -> !CompositeValidator.hasValue(r) || r.length() >= 3, "keyword cannot be less than 3 characters")
                .validate(name);
        validate(violations);

        return adminRepo.searchByName(name).stream()
                .map(adminMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AdminEntity updateEntity(AdminEntity entity, AdminUpdateRequestDto requestDto) { //DONE

        entity.getUser().setFirstName(isNull(requestDto.getFirstName()) ? entity.getUser().getFirstName() : requestDto.getFirstName());
        entity.getUser().setLastName(isNull(requestDto.getLastName()) ? entity.getUser().getLastName() : requestDto.getLastName());
        entity.getUser().setEmail(isNull(requestDto.getEmail()) ? entity.getUser().getEmail() : requestDto.getEmail());
        entity.getUser().setUserName(isNull(requestDto.getEmail()) ? entity.getUser().getEmail() : requestDto.getEmail());
        entity.getUser().setFacebookUrl(isNull(requestDto.getFacebookUrl()) ? entity.getUser().getFacebookUrl() : requestDto.getFacebookUrl());
        entity.getUser().setPhoneNumber(isNull(requestDto.getPhoneNumber()) ? entity.getUser().getPhoneNumber() : requestDto.getPhoneNumber());
        entity.setIban(isNull(requestDto.getIban()) ? entity.getIban() : requestDto.getIban());
        entity.setAddress(isNull(requestDto.getAddress()) ? entity.getAddress() : requestDto.getAddress());

        entity.getUser().setUpdateDate(new Timestamp(System.currentTimeMillis()));
        entity.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        return entity;
    }

    @Override
    public AdminRepo getRepo() {
        return adminRepo;
    }

    @Override
    public AdminMapper getMapper() {
        return adminMapper;
    }

    @Override
    public void preAddValidation(AdminRequestDto requestDto) { //DONE

        List<String> violations = new CompositeValidator<AdminRequestDto, String>()
                .addValidator(r -> CompositeValidator.hasValue(r.getFirstName()), "firstName cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getLastName()), "lastName cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getEmail()), "email cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getPhoneNumber()), "phoneNumber cannot be null")
                .addValidator(r -> isNull(r.getEmail()) || !userRepo.findByIsActiveAndEmail(true, r.getEmail()).isPresent(), "user with this email already exists")
                .addValidator(r -> isNull(r.getEmail()) || EmailHelper.validateEmail(r.getEmail()), "Email must have an email format")
                .addValidator(r -> nonNull(r.getRoleId()), "role ID cannot be null")
                .addValidator(r -> isNull(r.getRoleId()) || userRoleRepo.findById(r.getRoleId()).isPresent(), "No Role found with ID :" +requestDto.getRoleId())
                .validate(requestDto);
        validate(violations);

        if(!userRoleRepo.findById(requestDto.getRoleId()).get().getGroups().equals(Group.ADMINS)){
            throw new ValidationException("Entered Non Admin role on admin creation");
        }
    }

    @Override
    public void preUpdateValidation(AdminUpdateRequestDto requestDto) { //DONE

        List<String> violations = new CompositeValidator<AdminUpdateRequestDto, String>()
                .addValidator(r -> CompositeValidator.hasValue(r.getFirstName()), "firstName cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getLastName()), "lastName cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getEmail()), "email cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getPhoneNumber()), "phoneNumber cannot be null")
                .validate(requestDto);
        validate(violations);
    }

    @Override
    public void preUpdate(AdminEntity admin, AdminUpdateRequestDto updateDto) {
        if(nonNull(updateDto.getEmail())){
            UserEntity user = userRepo.findByIsActiveAndEmail(true, updateDto.getEmail())
                    .orElse(null);
            if(nonNull(user)){
                if(!admin.getUser().getId().equals(user.getId()))
                    throw new ValidationException("Email Already exists");
            }
        }
    }

    @Override
    public void cascadeDelete(Long id) {
        AdminEntity admin = adminRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("No admin found with ID:" + id));
        userRepo.softDelete(admin.getUser().getId());
    }

    @Override
    public void postSave(AdminRequestDto requestDto, AdminEntity entity) { //DONE

        String password = PasswordHelper.generatePassword();
        entity.getUser().setPassword(passwordEncoder.encode(password));
        mailService.sendPasswordEmailWithHtml(entity.getUser().getFirstName(), entity.getUser().getEmail(), password);
        userRepo.save(entity.getUser());
        adminRepo.save(entity);
    }
}
