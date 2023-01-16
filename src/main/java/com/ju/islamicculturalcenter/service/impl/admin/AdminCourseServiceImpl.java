package com.ju.islamicculturalcenter.service.impl.admin;

import com.ju.islamicculturalcenter.dto.request.admin.course.AdminCourseRequestDto;
import com.ju.islamicculturalcenter.dto.request.admin.course.AdminUpdateCourseRequestDto;
import com.ju.islamicculturalcenter.dto.response.admin.course.AdminCourseResponseDto;
import com.ju.islamicculturalcenter.entity.CourseEntity;
import com.ju.islamicculturalcenter.exceptions.ValidationException;
import com.ju.islamicculturalcenter.mappers.admin.AdminCourseMapper;
import com.ju.islamicculturalcenter.mappers.admin.AdminInstructorMapper;
import com.ju.islamicculturalcenter.mappers.admin.AdminStudentMapper;
import com.ju.islamicculturalcenter.repos.CourseRepo;
import com.ju.islamicculturalcenter.repos.InstructorCoursesRepo;
import com.ju.islamicculturalcenter.repos.StudentCoursesRepo;
import com.ju.islamicculturalcenter.repos.UserRoleRepo;
import com.ju.islamicculturalcenter.service.BaseServiceImpl;
import com.ju.islamicculturalcenter.service.helper.CompositeValidator;
import com.ju.islamicculturalcenter.service.iservice.admin.AdminCourseService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class AdminCourseServiceImpl extends BaseServiceImpl<CourseEntity, AdminCourseRequestDto, AdminCourseResponseDto, AdminUpdateCourseRequestDto> implements AdminCourseService {

    private final CourseRepo courseRepo;
    private final AdminCourseMapper adminCourseMapper;
    private final StudentCoursesRepo studentCoursesRepo;
    private final InstructorCoursesRepo instructorCoursesRepo;
    private final AdminInstructorMapper adminInstructorMapper;
    private final AdminStudentMapper adminStudentMapper;
    private final UserRoleRepo userRoleRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminCourseServiceImpl(CourseRepo courseRepo, StudentCoursesRepo studentCoursesRepo, InstructorCoursesRepo instructorCoursesRepo, UserRoleRepo userRoleRepo) {
        this.courseRepo = courseRepo;
        this.studentCoursesRepo = studentCoursesRepo;
        this.instructorCoursesRepo = instructorCoursesRepo;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.adminInstructorMapper = new AdminInstructorMapper(bCryptPasswordEncoder, userRoleRepo);
        this.adminStudentMapper = new AdminStudentMapper(userRoleRepo);
        this.userRoleRepo = userRoleRepo;
        this.adminCourseMapper = new AdminCourseMapper(studentCoursesRepo, instructorCoursesRepo, userRoleRepo);
    }

    @Override
    public List<AdminCourseResponseDto> searchCourseByName(String keyword) {

        List<String> violations = new CompositeValidator<String, String>()
                .addValidator(CompositeValidator::hasValue, "keyword cannot be empty")
                .addValidator(r -> !CompositeValidator.hasValue(r) || r.length() >= 3, "keyword cannot be less than 3 characters")
                .validate(keyword);
        validate(violations);

        return courseRepo.searchByName(keyword).stream()
                .map(adminCourseMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CourseEntity updateEntity(CourseEntity entity, AdminUpdateCourseRequestDto dto) {

        entity.setName(nonNull(dto.getName()) ? dto.getName() : entity.getName());
        entity.setDescription(nonNull(dto.getDescription()) ? dto.getDescription() : entity.getDescription());
        entity.setDuration(nonNull(dto.getDuration()) ? dto.getDuration() : entity.getDuration());
        entity.setStartDate(nonNull(dto.getStartDate()) ? dto.getStartDate() : entity.getStartDate());
        entity.setEndDate(nonNull(dto.getEndDate()) ? dto.getEndDate() : entity.getEndDate());
        entity.setLectureTime(nonNull(dto.getLectureTime()) ? dto.getLectureTime() : entity.getLectureTime());
        entity.setDaysOfWeek(nonNull(dto.getDaysOfWeek()) ? dto.getDaysOfWeek() : entity.getDaysOfWeek());
        entity.setCategory(nonNull(dto.getCategory()) ? dto.getCategory() : entity.getCategory());
        entity.setMaxParticipants(nonNull(dto.getMaxParticipants()) ? dto.getMaxParticipants() : entity.getMaxParticipants());
        entity.setIsPreRecorded(nonNull(dto.getIsPreRecorded()) ? dto.getIsPreRecorded() : entity.getIsPreRecorded());
        entity.setIsOnline(nonNull(dto.getIsOnline()) ? dto.getIsOnline() : entity.getIsOnline());
        entity.setIsFree(nonNull(dto.getIsFree()) ? dto.getIsFree() : entity.getIsFree());
        entity.setPrice(nonNull(dto.getPrice()) ? dto.getPrice() : entity.getPrice());
        entity.setClassroom(nonNull(dto.getClassroom()) ? dto.getClassroom() : entity.getClassroom());
        entity.setSemester(nonNull(dto.getSemester()) ? dto.getSemester() : entity.getSemester());
        entity.setYear(nonNull(dto.getYear()) ? dto.getYear() : entity.getYear());
        entity.setTeamsLink(nonNull(dto.getTeamsLink()) ? dto.getTeamsLink() : entity.getTeamsLink());
        entity.setLastRegDay(nonNull(dto.getLastRegDay()) ? dto.getLastRegDay() : entity.getLastRegDay());

        entity.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        return entity;
    }

    @Override
    public CourseRepo getRepo() {
        return courseRepo;
    }

    @Override
    public AdminCourseMapper getMapper() {
        return adminCourseMapper;
    }

    @Override
    public void preAddValidation(AdminCourseRequestDto dto) {

        List<String> violations = new CompositeValidator<AdminCourseRequestDto, String>()
                .addValidator(r -> CompositeValidator.hasValue(r.getName()), "name cannot be empty")
                .addValidator(r -> !CompositeValidator.hasValue(r.getName()) || !courseRepo.findByName(r.getName()).isPresent(), "course with this name already exists")
                .addValidator(r -> CompositeValidator.hasValue(r.getDescription()), "description cannot be empty")
                .addValidator(r -> nonNull(r.getDuration()), "duration cannot be empty")
                .addValidator(r -> nonNull(r.getStartDate()), "startDate cannot be empty")
                .addValidator(r -> nonNull(r.getEndDate()), "endDate cannot be empty")
                .addValidator(r -> isNull(r.getEndDate()) || isNull(r.getStartDate()) || !r.getEndDate().isBefore(r.getStartDate()), "endDate cannot be before startDate")
                .addValidator(r -> isNull(r.getStartDate()) || !r.getStartDate().isBefore(LocalDate.now()), "startDate cannot be in the past")
                .addValidator(r -> isNull(r.getEndDate()) || !r.getEndDate().isBefore(LocalDate.now()), "endDate cannot be in the past")
                .addValidator(r -> nonNull(r.getLectureTime()), "lectureTime cannot be empty")
                .addValidator(r -> nonNull(r.getDaysOfWeek()), "daysOfWeek cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getCategory()), "category Cannot be null")
                .addValidator(r -> nonNull(r.getMaxParticipants()), "maxParticipants Cannot be null")
                .addValidator(r -> isNull(r.getMaxParticipants()) || r.getMaxParticipants() > 1, "maxParticipants Cannot be less than 1")
                .addValidator(r -> (isNull(r.getIsFree())) || !(r.getIsFree().equals(Boolean.FALSE) && (isNull(r.getPrice()) || r.getPrice().equals(0.0))), "Price Cannot be empty if course is not free")
                .addValidator(r -> CompositeValidator.hasValue(r.getClassroom()), "classroom cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getSemester()), "semester cannot be null")
                .addValidator(r -> nonNull(r.getYear()), "year cannot be null")
                .addValidator(r -> isNull(r.getYear()) || !r.getYear().with(lastDayOfYear()).isBefore(LocalDate.now()), "year cannot be in the past")
                .addValidator(r -> nonNull(r.getLastRegDay()), "lastRegDay cannot be null")
                .addValidator(r -> isNull(r.getLastRegDay()) || !r.getLastRegDay().isBefore(LocalDate.now().plusDays(1L)), "lastRegDay cannot be in the past or 1 day from now")
                .validate(dto);
        validate(violations);
    }

    @Override
    public void preUpdate(CourseEntity entity, AdminUpdateCourseRequestDto updateDto) {
        if(nonNull(updateDto.getName()) && !entity.getName().equals(updateDto.getName())){
            if(courseRepo.findByName(updateDto.getName()).isPresent())
                throw new ValidationException("course with this name already exists");
        }
    }

    @Override
    public void preUpdateValidation(AdminUpdateCourseRequestDto dto) {

        List<String> violations = new CompositeValidator<AdminUpdateCourseRequestDto, String>()
                .addValidator(r -> CompositeValidator.hasValue(r.getName()), "name cannot be empty")
                .addValidator(r -> CompositeValidator.hasValue(r.getDescription()), "description cannot be empty")
                .addValidator(r -> nonNull(r.getDuration()), "duration cannot be empty")
                .addValidator(r -> nonNull(r.getStartDate()), "startDate cannot be empty")
                .addValidator(r -> nonNull(r.getEndDate()), "endDate cannot be empty")
                .addValidator(r -> isNull(r.getEndDate()) || isNull(r.getStartDate()) || !r.getEndDate().isBefore(r.getStartDate()), "endDate cannot be before startDate")
                .addValidator(r -> isNull(r.getStartDate()) || !r.getStartDate().isBefore(LocalDate.now()), "startDate cannot be in the past")
                .addValidator(r -> isNull(r.getEndDate()) || !r.getEndDate().isBefore(LocalDate.now()), "endDate cannot be in the past")
                .addValidator(r -> nonNull(r.getLectureTime()), "lectureTime cannot be empty")
                .addValidator(r -> nonNull(r.getDaysOfWeek()), "daysOfWeek cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getCategory()), "category Cannot be null")
                .addValidator(r -> nonNull(r.getMaxParticipants()), "maxParticipants Cannot be null")
                .addValidator(r -> isNull(r.getMaxParticipants()) || r.getMaxParticipants() > 1, "maxParticipants Cannot be less than 1")
                .addValidator(r -> (isNull(r.getIsFree())) || !(r.getIsFree().equals(Boolean.FALSE) && (isNull(r.getPrice()) || r.getPrice().equals(0.0))), "Price Cannot be empty if course is not free")
                .addValidator(r -> CompositeValidator.hasValue(r.getClassroom()), "classroom cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getSemester()), "semester cannot be null")
                .addValidator(r -> nonNull(r.getYear()), "year cannot be null")
                .addValidator(r -> isNull(r.getYear()) || !r.getYear().isBefore(LocalDate.now()), "year cannot be in the past")
                .addValidator(r -> nonNull(r.getLastRegDay()), "lastRegDay cannot be null")
                .addValidator(r -> isNull(r.getLastRegDay()) || !r.getLastRegDay().isBefore(LocalDate.now().plusDays(1L)), "lastRegDay cannot be in the past or 1 day from now")
                .validate(dto);
        validate(violations);
    }

}
