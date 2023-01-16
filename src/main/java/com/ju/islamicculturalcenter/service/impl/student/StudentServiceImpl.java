package com.ju.islamicculturalcenter.service.impl.student;

import com.ju.islamicculturalcenter.dto.request.student.StudentRegistrationRequestDto;
import com.ju.islamicculturalcenter.dto.request.student.StudentUpdateProfileRequest;
import com.ju.islamicculturalcenter.dto.response.student.StudentRegistrationResponseDto;
import com.ju.islamicculturalcenter.entity.StudentEntity;
import com.ju.islamicculturalcenter.mappers.BaseMapper;
import com.ju.islamicculturalcenter.mappers.student.StudentRegistrationMapper;
import com.ju.islamicculturalcenter.repos.BaseRepo;
import com.ju.islamicculturalcenter.repos.StudentRepo;
import com.ju.islamicculturalcenter.repos.UserRoleRepo;
import com.ju.islamicculturalcenter.service.BaseServiceImpl;
import com.ju.islamicculturalcenter.service.iservice.student.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends BaseServiceImpl<StudentEntity, StudentRegistrationRequestDto, StudentRegistrationResponseDto, StudentUpdateProfileRequest> implements StudentService {

    private final StudentRepo studentRepo;
    private final StudentRegistrationMapper studentRegistrationMapper;
    private final UserRoleRepo userRoleRepo;

    public StudentServiceImpl(StudentRepo studentRepo, UserRoleRepo userRoleRepo) {
        this.studentRepo = studentRepo;
        this.userRoleRepo = userRoleRepo;
        studentRegistrationMapper = new StudentRegistrationMapper(this.userRoleRepo);
    }

    @Override
    public StudentEntity updateEntity(StudentEntity entity, StudentUpdateProfileRequest dto) {
        //return entity.setFirstName(nonNull(dto.getFirstName()) ? );
        return null;
    }

    @Override
    public BaseRepo<StudentEntity, Long> getRepo() {
        return studentRepo;
    }

    @Override
    public BaseMapper<StudentEntity, StudentRegistrationRequestDto, StudentRegistrationResponseDto> getMapper() {
        return studentRegistrationMapper;
    }

    @Override
    public void preAddValidation(StudentRegistrationRequestDto dto) {

    }

    @Override
    public void preUpdateValidation(StudentUpdateProfileRequest dto) {

    }
}
