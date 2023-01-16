package com.ju.islamicculturalcenter.service.impl.admin;

import com.ju.islamicculturalcenter.dto.request.admin.instructor.AdminInstructorRequestDto;
import com.ju.islamicculturalcenter.dto.request.admin.instructor.AdminInstructorUpdateRequestDto;
import com.ju.islamicculturalcenter.dto.request.admin.instructor.AdminResetInstructorPasswordRequestDto;
import com.ju.islamicculturalcenter.dto.response.admin.instructor.AdminInstructorResponseDto;
import com.ju.islamicculturalcenter.entity.InstructorEntity;
import com.ju.islamicculturalcenter.entity.UserEntity;
import com.ju.islamicculturalcenter.exceptions.NotFoundException;
import com.ju.islamicculturalcenter.exceptions.ValidationException;
import com.ju.islamicculturalcenter.mappers.admin.AdminInstructorMapper;
import com.ju.islamicculturalcenter.repos.InstructorRepo;
import com.ju.islamicculturalcenter.repos.UserRepo;
import com.ju.islamicculturalcenter.repos.UserRoleRepo;
import com.ju.islamicculturalcenter.service.BaseServiceImpl;
import com.ju.islamicculturalcenter.service.helper.CompositeValidator;
import com.ju.islamicculturalcenter.service.helper.EmailHelper;
import com.ju.islamicculturalcenter.service.helper.PasswordHelper;
import com.ju.islamicculturalcenter.service.iservice.MailService;
import com.ju.islamicculturalcenter.service.iservice.admin.AdminInstructorService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class AdminInstructorServiceImpl extends BaseServiceImpl<InstructorEntity, AdminInstructorRequestDto, AdminInstructorResponseDto, AdminInstructorUpdateRequestDto> implements AdminInstructorService {

    private final InstructorRepo instructorRepo;
    private final AdminInstructorMapper adminInstructorMapper;
    private final UserRoleRepo userRoleRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MailService mailService;
    private final UserRepo userRepo;

    public AdminInstructorServiceImpl(InstructorRepo instructorRepo, UserRoleRepo userRoleRepo, BCryptPasswordEncoder bCryptPasswordEncoder, MailService mailService, UserRepo userRepo) {
        this.instructorRepo = instructorRepo;
        this.userRoleRepo = userRoleRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailService = mailService;
        this.userRepo = userRepo;
        adminInstructorMapper = new AdminInstructorMapper(this.bCryptPasswordEncoder, this.userRoleRepo);
    }

    @Override
    public void resetPassword(AdminResetInstructorPasswordRequestDto requestDto) {

        List<String> violations = new CompositeValidator<AdminResetInstructorPasswordRequestDto, String>()
                .addValidator(r -> nonNull(r.getId()), "ID cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getNewPassword()), "newPassword cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getConfirmPassword()), "confirmPassword cannot be null")
                .addValidator(r -> isNull(r.getNewPassword()) || isNull(r.getConfirmPassword()) || r.getNewPassword().equals(r.getConfirmPassword()), "password does not match")
                .addValidator(r -> isNull(r.getNewPassword()) || PasswordHelper.validatePassword(r.getNewPassword()), "password should be 8-20 characters with 1 capital letter")
                .validate(requestDto);
        validate(violations);

        InstructorEntity instructor = instructorRepo.findByIdAndIsActive(requestDto.getId(), true)
                .orElseThrow(() -> new NotFoundException("No Instructor Found with ID: " + requestDto.getId()));

        instructor.getUser().setPassword(bCryptPasswordEncoder.encode(requestDto.getNewPassword()));
        instructor.getUser().setUpdateDate(new Timestamp(System.currentTimeMillis()));
        instructor.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        mailService.sendPasswordEmail(instructor.getUser().getFirstName(), instructor.getUser().getEmail(), requestDto.getNewPassword());

        instructorRepo.save(instructor);
    }

    @Override
    public List<AdminInstructorResponseDto> searchInstructorByName(String name) {

        List<String> violations = new CompositeValidator<String, String>()
                .addValidator(CompositeValidator::hasValue, "keyword cannot be empty")
                .addValidator(r -> !CompositeValidator.hasValue(r) || r.length() >= 3, "keyword cannot be less than 3 characters")
                .validate(name);
        validate(violations);

        return instructorRepo.searchByName(name).stream()
                .map(adminInstructorMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public InstructorEntity updateEntity(InstructorEntity entity, AdminInstructorUpdateRequestDto dto) {
        entity.getUser().setFirstName(isNull(dto.getFirstName()) ? entity.getUser().getFirstName() : dto.getFirstName());
        entity.getUser().setLastName(isNull(dto.getLastName()) ? entity.getUser().getLastName() : dto.getLastName());
        entity.getUser().setEmail(isNull(dto.getEmail()) ? entity.getUser().getEmail() : dto.getEmail());
        entity.getUser().setUserName(isNull(dto.getEmail()) ? entity.getUser().getEmail() : dto.getEmail());
        entity.getUser().setPhoneNumber(isNull(dto.getPhoneNumber()) ? entity.getUser().getPhoneNumber() : dto.getPhoneNumber());
        entity.getUser().setFacebookUrl(isNull(dto.getFacebookUrl()) ? entity.getUser().getFacebookUrl() : dto.getFacebookUrl());
        entity.setImageUrl(isNull(dto.getImageUrl()) ? entity.getImageUrl() : dto.getImageUrl());
        entity.setIsVolunteer(isNull(dto.getIsVolunteer()) ? entity.getIsVolunteer() : dto.getIsVolunteer());
        entity.setSalary(isNull(dto.getSalary()) ? entity.getSalary() : dto.getSalary());
        entity.setCvUrl(isNull(dto.getCvUrl()) ? entity.getCvUrl() : dto.getCvUrl());
        entity.setSubNumber(isNull(dto.getSubNumber()) ? entity.getSubNumber() : dto.getSubNumber());

        entity.getUser().setUpdateDate(new Timestamp(System.currentTimeMillis()));
        entity.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        return entity;
    }

    @Override
    public InstructorRepo getRepo() {
        return instructorRepo;
    }

    @Override
    public AdminInstructorMapper getMapper() {
        return adminInstructorMapper;
    }

    @Override
    public void preAddValidation(AdminInstructorRequestDto requestDto) {

        List<String> violations = new CompositeValidator<AdminInstructorRequestDto, String>()
                .addValidator(r -> CompositeValidator.hasValue(r.getFirstName()), "firstName cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getLastName()), "lastName cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getEmail()), "email cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getPhoneNumber()), "phoneNumber cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getPassword()), "password cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getImageUrl()), "image cannot be null")
                .addValidator(r -> isNull(r.getEmail()) || !userRepo.findByIsActiveAndEmail(true, r.getEmail()).isPresent(), "user with this email already exists")
                .addValidator(r -> isNull(r.getEmail()) || EmailHelper.validateEmail(r.getEmail()), "Email must have an email format")
                .addValidator(r -> isNull(r.getPassword()) || PasswordHelper.validatePassword(r.getPassword()), "Password should be 8-20 with 1 capital letter")
                .addValidator(r -> nonNull(r.getIsVolunteer()), "isVolunteer cannot be null")
                .addValidator(r -> (isNull(r.getIsVolunteer())) || !(r.getIsVolunteer().equals(Boolean.FALSE) && (isNull(r.getSalary()) || r.getSalary().equals(0.0))), "Salary Cannot be empty if instructor is not volunteer")
                .validate(requestDto);
        validate(violations);
    }

    @Override
    public void preUpdateValidation(AdminInstructorUpdateRequestDto requestDto) {

        List<String> violations = new CompositeValidator<AdminInstructorUpdateRequestDto, String>()
                .addValidator(r -> CompositeValidator.hasValue(r.getFirstName()), "firstName cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getLastName()), "lastName cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getEmail()), "email cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getPhoneNumber()), "phoneNumber cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getImageUrl()), "image cannot be null")
                .addValidator(r -> isNull(r.getEmail()) || EmailHelper.validateEmail(r.getEmail()), "Email must have an email format")
                .addValidator(r -> nonNull(r.getIsVolunteer()), "isVolunteer cannot be null")
                .addValidator(r -> (isNull(r.getIsVolunteer())) || !(r.getIsVolunteer().equals(Boolean.FALSE) && (isNull(r.getSalary()) || r.getSalary().equals(0.0))), "Salary Cannot be empty if instructor is not volunteer")
                .validate(requestDto);
        validate(violations);

    }

    @Override
    public void preUpdate(InstructorEntity instructor, AdminInstructorUpdateRequestDto updateDto) {
        if (nonNull(updateDto.getEmail())) {
            UserEntity user = userRepo.findByIsActiveAndEmail(true, updateDto.getEmail())
                    .orElse(null);
            if (nonNull(user)) {
                if (!instructor.getUser().getId().equals(user.getId()))
                    throw new ValidationException("Email Already exists");
            }
        }
    }

    @Override
    public void cascadeDelete(Long id) {
        InstructorEntity instructor = instructorRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("No admin found with ID:" + id));
        userRepo.softDelete(instructor.getUser().getId());
    }

    @Override
    public void postSave(AdminInstructorRequestDto requestDto, InstructorEntity entity) { //DONE
        mailService.sendPasswordEmail(entity.getUser().getFirstName(), entity.getUser().getEmail(), requestDto.getPassword());
    }
}
