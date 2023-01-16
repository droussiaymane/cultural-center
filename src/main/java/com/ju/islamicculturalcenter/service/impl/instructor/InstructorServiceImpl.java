package com.ju.islamicculturalcenter.service.impl.instructor;

import com.ju.islamicculturalcenter.dto.request.instructor.profile.InstructorRequestDto;
import com.ju.islamicculturalcenter.dto.request.instructor.profile.InstructorUpdatePassword;
import com.ju.islamicculturalcenter.dto.request.instructor.profile.InstructorUpdateProfileDto;
import com.ju.islamicculturalcenter.dto.response.instructor.profile.InstructorResponseDto;
import com.ju.islamicculturalcenter.entity.InstructorEntity;
import com.ju.islamicculturalcenter.entity.UserEntity;
import com.ju.islamicculturalcenter.exceptions.NotFoundException;
import com.ju.islamicculturalcenter.exceptions.ValidationException;
import com.ju.islamicculturalcenter.mappers.BaseMapper;
import com.ju.islamicculturalcenter.mappers.instructor.InstructorMapper;
import com.ju.islamicculturalcenter.repos.BaseRepo;
import com.ju.islamicculturalcenter.repos.InstructorRepo;
import com.ju.islamicculturalcenter.repos.UserRoleRepo;
import com.ju.islamicculturalcenter.service.BaseServiceImpl;
import com.ju.islamicculturalcenter.service.auth.UserDetailsUtil;
import com.ju.islamicculturalcenter.service.helper.CompositeValidator;
import com.ju.islamicculturalcenter.service.iservice.instructor.InstructorService;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static com.ju.islamicculturalcenter.service.helper.CompositeValidator.hasValue;
import static java.util.Objects.nonNull;

@Service
public class InstructorServiceImpl extends BaseServiceImpl<InstructorEntity, InstructorRequestDto, InstructorResponseDto, InstructorUpdateProfileDto> implements InstructorService {

    private final InstructorRepo instructorRepo;
    private final InstructorMapper instructorMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRoleRepo userRoleRepo;

    public InstructorServiceImpl(InstructorRepo irepo, BCryptPasswordEncoder bCryptPasswordEncoder, UserRoleRepo userRoleRepo) {
        instructorRepo = irepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRoleRepo = userRoleRepo;
        this.instructorMapper = new InstructorMapper(this.bCryptPasswordEncoder, this.userRoleRepo);
    }

    @Override
    public void updatePassword(InstructorUpdatePassword request) {

        List<String> violations = new CompositeValidator<InstructorUpdatePassword, String>()
                .addValidator(r -> hasValue(r.getOldPassword()), "Old Password Cannot Be Empty")
                .addValidator(r -> hasValue(r.getNewPassword()), "New Password Cannot Be Empty")
                .addValidator(r -> hasValue(r.getConfirmPassword()), "Confirm Password Cannot Be Empty")
                .addValidator(r -> !hasValue(r.getNewPassword()) || !hasValue(r.getConfirmPassword()) || r.getNewPassword().equals(r.getConfirmPassword()), "New Password And Confirm Password Do Not Match")
                .validate(request);
        validate(violations);

        InstructorEntity instructor = instructorRepo.findOne(Example.of(InstructorEntity.builder()
                .active(true)
                .user(UserEntity.builder().id(UserDetailsUtil.userDetails().getId()).build())
                .build())).orElseThrow(() -> new NotFoundException("no instructor found with current session"));

        if (!bCryptPasswordEncoder.matches(request.getOldPassword(), instructor.getUser().getPassword()))
            throw new ValidationException("Old Password does not match");

        instructor.getUser().setPassword(bCryptPasswordEncoder.encode(request.getConfirmPassword()));
        instructor.getUser().setUpdateDate(new Timestamp(System.currentTimeMillis()));
        instructor.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        instructorRepo.save(instructor);
    }

    @Override
    public InstructorResponseDto viewProfile() {
        return instructorMapper.mapEntityToDto(instructorRepo.findOne(Example.of(InstructorEntity.builder()
                .active(true)
                .user(UserEntity.builder().id(UserDetailsUtil.userDetails().getId()).build())
                .build())).orElseThrow(() -> new NotFoundException("no instructor found with current session")));
    }

    @Override
    public InstructorEntity updateEntity(InstructorEntity entity, InstructorUpdateProfileDto dto) {

        entity.getUser().setFirstName(dto.getFirstName());
        entity.getUser().setLastName(dto.getLastName());
        entity.getUser().setPhoneNumber(dto.getPhoneNumber());
        entity.getUser().setFacebookUrl(nonNull(dto.getFacebookUrl()) ? dto.getFacebookUrl() : entity.getUser().getFacebookUrl());
        entity.setImageUrl(nonNull(dto.getImageUrl()) ? dto.getImageUrl() : entity.getImageUrl());
        entity.setCvUrl(nonNull(dto.getCvUrl()) ? dto.getCvUrl() : entity.getCvUrl());

        entity.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        return entity;
    }

    @Override
    public InstructorResponseDto update(Long id, InstructorUpdateProfileDto dto) {
        InstructorEntity instructor = instructorRepo.findOne(Example.of(InstructorEntity.builder()
                .user(UserEntity.builder().id(id).build()).build()))
                .orElseThrow(() -> new NotFoundException("No user found with this session"));

        return super.update(instructor.getId(), dto);
    }

    @Override
    public BaseRepo<InstructorEntity, Long> getRepo() {
        return instructorRepo;
    }

    @Override
    public BaseMapper<InstructorEntity, InstructorRequestDto, InstructorResponseDto> getMapper() {
        return instructorMapper;
    }

    @Override
    public void preAddValidation(InstructorRequestDto dto) {

    }

    @Override
    public void preUpdateValidation(InstructorUpdateProfileDto dto) {

        List<String> violations = new CompositeValidator<InstructorUpdateProfileDto, String>()
                .addValidator(r -> hasValue(r.getFirstName()), "first name Cannot Be Empty")
                .addValidator(r -> hasValue(r.getLastName()), "last name Cannot Be Empty")
                .addValidator(r -> hasValue(r.getPhoneNumber()), "phone number Cannot Be Empty")
                .validate(dto);
        validate(violations);
    }
}
