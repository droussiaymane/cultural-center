package com.ju.islamicculturalcenter.service.impl.admin;

import com.ju.islamicculturalcenter.dto.request.admin.student.AdminResetStudentPasswordRequestDto;
import com.ju.islamicculturalcenter.dto.request.admin.student.AdminStudentRequestDto;
import com.ju.islamicculturalcenter.dto.request.admin.student.AdminUpdateStudentRequestDto;
import com.ju.islamicculturalcenter.dto.response.admin.AdminStudentResponseDto;
import com.ju.islamicculturalcenter.entity.StudentEntity;
import com.ju.islamicculturalcenter.entity.UserEntity;
import com.ju.islamicculturalcenter.exceptions.NotFoundException;
import com.ju.islamicculturalcenter.exceptions.ValidationException;
import com.ju.islamicculturalcenter.mappers.admin.AdminStudentMapper;
import com.ju.islamicculturalcenter.repos.StudentRepo;
import com.ju.islamicculturalcenter.repos.UserRepo;
import com.ju.islamicculturalcenter.repos.UserRoleRepo;
import com.ju.islamicculturalcenter.service.BaseServiceImpl;
import com.ju.islamicculturalcenter.service.helper.CompositeValidator;
import com.ju.islamicculturalcenter.service.helper.EmailHelper;
import com.ju.islamicculturalcenter.service.helper.PasswordHelper;
import com.ju.islamicculturalcenter.service.iservice.MailService;
import com.ju.islamicculturalcenter.service.iservice.admin.AdminStudentService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class AdminStudentServiceImp extends BaseServiceImpl<StudentEntity, AdminStudentRequestDto, AdminStudentResponseDto, AdminUpdateStudentRequestDto> implements AdminStudentService {

    private final StudentRepo studentRepo;
    private final AdminStudentMapper adminStudentMapper;
    private final UserRoleRepo userRoleRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final UserRepo userRepo;

    public AdminStudentServiceImp(StudentRepo studentRepo, UserRoleRepo userRoleRepo, BCryptPasswordEncoder passwordEncoder, MailService mailService, UserRepo userRepo) {
        this.studentRepo = studentRepo;
        this.userRoleRepo = userRoleRepo;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.userRepo = userRepo;
        this.adminStudentMapper = new AdminStudentMapper(this.userRoleRepo);
    }

    @Override
    public void resetPassword(AdminResetStudentPasswordRequestDto requestDto) {

        List<String> violations = new CompositeValidator<AdminResetStudentPasswordRequestDto, String>()
                .addValidator(r -> nonNull(r.getId()), "ID cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getNewPassword()), "newPassword cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getConfirmPassword()), "confirmPassword cannot be null")
                .addValidator(r -> isNull(r.getNewPassword()) || isNull(r.getConfirmPassword()) || r.getNewPassword().equals(r.getConfirmPassword()), "password does not match")
                .addValidator(r -> isNull(r.getNewPassword()) || PasswordHelper.validatePassword(r.getNewPassword()), "password should be 8-20 characters with 1 capital letter")
                .validate(requestDto);
        validate(violations);

        StudentEntity student = studentRepo.findByIdAndIsActive(requestDto.getId(), true)
                .orElseThrow(() -> new NotFoundException("No Student Found with ID: " + requestDto.getId()));

        student.getUser().setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        student.getUser().setUpdateDate(new Timestamp(System.currentTimeMillis()));
        student.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        mailService.sendPasswordEmail(student.getUser().getFirstName(), student.getUser().getEmail(), requestDto.getNewPassword());

        studentRepo.save(student);
    }

    @Override
    public List<AdminStudentResponseDto> searchStudentByName(String name) {

        List<String> violations = new CompositeValidator<String, String>()
                .addValidator(CompositeValidator::hasValue, "keyword cannot be empty")
                .addValidator(r -> !CompositeValidator.hasValue(r) || r.length() >= 3, "keyword cannot be less than 3 characters")
                .validate(name);
        validate(violations);

        return studentRepo.searchByName(name).stream()
                .map(adminStudentMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentEntity updateEntity(StudentEntity entity, AdminUpdateStudentRequestDto dto) {

        entity.getUser().setFirstName(nonNull(dto.getFirstName()) ? dto.getFirstName() : entity.getUser().getFirstName());
        entity.getUser().setLastName(nonNull(dto.getLastName()) ? dto.getLastName() : entity.getUser().getLastName());
        entity.getUser().setEmail(nonNull(dto.getEmail()) ? dto.getEmail() : entity.getUser().getEmail());
        entity.getUser().setUserName(nonNull(dto.getEmail()) ? dto.getEmail() : entity.getUser().getEmail());
        entity.getUser().setPhoneNumber(nonNull(dto.getPhoneNumber()) ? dto.getPhoneNumber() : entity.getUser().getPhoneNumber());
        entity.getUser().setFacebookUrl(nonNull(dto.getFacebookUrl()) ? dto.getFacebookUrl() : entity.getUser().getFacebookUrl());
        entity.setDateOfBirth(nonNull(dto.getDateOfBirth()) ? dto.getDateOfBirth() : entity.getDateOfBirth());

        entity.getUser().setUpdateDate(new Timestamp(System.currentTimeMillis()));
        entity.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        return entity;
    }

    @Override
    public StudentRepo getRepo() {
        return studentRepo;
    }

    @Override
    public AdminStudentMapper getMapper() {
        return adminStudentMapper;
    }

    @Override
    public void preAddValidation(AdminStudentRequestDto requestDto) {

        List<String> violations = new CompositeValidator<AdminStudentRequestDto, String>()
                .addValidator(r -> CompositeValidator.hasValue(r.getFirstName()), "firstName cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getLastName()), "lastName cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getEmail()), "email cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getPhoneNumber()), "phoneNumber cannot be null")
                .addValidator(r -> nonNull(r.getDateOfBirth()), "dateOfBirth cannot be null")
                .addValidator(r -> isNull(r.getEmail()) || !userRepo.findByIsActiveAndEmail(true, r.getEmail()).isPresent(), "user with this email already exists")
                .addValidator(r -> isNull(r.getEmail()) || EmailHelper.validateEmail(r.getEmail()), "Email must have an email format")
                .validate(requestDto);
        validate(violations);
    }

    @Override
    public void preUpdateValidation(AdminUpdateStudentRequestDto requestDto) {

        List<String> violations = new CompositeValidator<AdminUpdateStudentRequestDto, String>()
                .addValidator(r -> CompositeValidator.hasValue(r.getFirstName()), "firstName cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getLastName()), "lastName cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getEmail()), "email cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getPhoneNumber()), "phoneNumber cannot be null")
                .addValidator(r -> nonNull(r.getDateOfBirth()), "dateOfBirth cannot be null")
                .validate(requestDto);
        validate(violations);
    }

    @Override
    public void preUpdate(StudentEntity instructor, AdminUpdateStudentRequestDto updateDto) {
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
        StudentEntity student = studentRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("No admin found with ID:" + id));
        userRepo.softDelete(student.getUser().getId());
    }

    @Override
    public void postSave(AdminStudentRequestDto requestDto, StudentEntity entity) { //DONE

        String password = PasswordHelper.generatePassword();
        entity.getUser().setPassword(passwordEncoder.encode(password));

        mailService.sendPasswordEmail(entity.getUser().getFirstName(), entity.getUser().getEmail(), password);

        userRepo.save(entity.getUser());
        studentRepo.save(entity);
    }
}
