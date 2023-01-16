package com.ju.islamicculturalcenter.mappers.admin;

import com.ju.islamicculturalcenter.dto.request.admin.course.AdminCourseRequestDto;
import com.ju.islamicculturalcenter.dto.response.admin.AdminStudentResponseDto;
import com.ju.islamicculturalcenter.dto.response.admin.course.AdminCourseResponseDto;
import com.ju.islamicculturalcenter.dto.response.admin.instructor.AdminInstructorResponseDto;
import com.ju.islamicculturalcenter.entity.CourseEntity;
import com.ju.islamicculturalcenter.entity.InstructorCoursesEntity;
import com.ju.islamicculturalcenter.entity.StudentCoursesEntity;
import com.ju.islamicculturalcenter.mappers.BaseMapper;
import com.ju.islamicculturalcenter.repos.InstructorCoursesRepo;
import com.ju.islamicculturalcenter.repos.StudentCoursesRepo;
import com.ju.islamicculturalcenter.repos.UserRoleRepo;
import com.ju.islamicculturalcenter.service.auth.UserDetailsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class AdminCourseMapper implements BaseMapper<CourseEntity, AdminCourseRequestDto, AdminCourseResponseDto> {

    private final StudentCoursesRepo studentCoursesRepo;
    private final InstructorCoursesRepo instructorCoursesRepo;
    private final AdminInstructorMapper adminInstructorMapper;
    private final AdminStudentMapper adminStudentMapper;
    private final UserRoleRepo userRoleRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminCourseMapper(StudentCoursesRepo studentCoursesRepo, InstructorCoursesRepo instructorCoursesRepo, UserRoleRepo userRoleRepo) {
        this.studentCoursesRepo = studentCoursesRepo;
        this.instructorCoursesRepo = instructorCoursesRepo;
        this.userRoleRepo = userRoleRepo;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.adminInstructorMapper = new AdminInstructorMapper(bCryptPasswordEncoder, userRoleRepo);
        this.adminStudentMapper = new AdminStudentMapper(userRoleRepo);
    }


    @Override
    public CourseEntity mapDtoToEntity(AdminCourseRequestDto adminCourseRequestDto) {
        return CourseEntity.builder()
                .active(true)
                .createdById(UserDetailsUtil.userDetails().getId())
                .updatedById(UserDetailsUtil.userDetails().getId())
                .creationDate(new Timestamp(System.currentTimeMillis()))
                .updateDate(new Timestamp(System.currentTimeMillis()))
                .name(adminCourseRequestDto.getName())
                .description(adminCourseRequestDto.getDescription())
                .duration(adminCourseRequestDto.getDuration())
                .startDate(adminCourseRequestDto.getStartDate())
                .endDate(adminCourseRequestDto.getEndDate())
                .lectureTime(adminCourseRequestDto.getLectureTime())
                .daysOfWeek(adminCourseRequestDto.getDaysOfWeek())
                .category(adminCourseRequestDto.getCategory())
                .maxParticipants(adminCourseRequestDto.getMaxParticipants())
                .isPreRecorded(adminCourseRequestDto.getIsPreRecorded())
                .isOnline(adminCourseRequestDto.getIsOnline())
                .isFree(adminCourseRequestDto.getIsFree())
                .price(adminCourseRequestDto.getIsFree() ? 0.0 : adminCourseRequestDto.getPrice())
                .classroom(adminCourseRequestDto.getClassroom())
                .semester(adminCourseRequestDto.getSemester())
                .year(adminCourseRequestDto.getYear())
                .teamsLink(adminCourseRequestDto.getTeamsLink())
                .lastRegDay(adminCourseRequestDto.getLastRegDay())
                .build();
    }

    @Override
    public AdminCourseResponseDto mapEntityToDto(CourseEntity courseEntity) {

        return AdminCourseResponseDto.builder()
                .id(courseEntity.getId())
                .creationDate(courseEntity.getCreationDate())
                .updateDate(courseEntity.getUpdateDate())
                .createdById(courseEntity.getCreatedById())
                .updatedById(courseEntity.getUpdatedById())
                .name(courseEntity.getName())
                .description(courseEntity.getDescription())
                .duration(courseEntity.getDuration())
                .startDate(courseEntity.getStartDate())
                .endDate(courseEntity.getEndDate())
                .lectureTime(courseEntity.getLectureTime())
                .daysOfWeek(courseEntity.getDaysOfWeek())
                .category(courseEntity.getCategory())
                .maxParticipants(courseEntity.getMaxParticipants())
                .isPreRecorded(courseEntity.getIsPreRecorded())
                .isOnline(courseEntity.getIsOnline())
                .isFree(courseEntity.getIsFree())
                .price(courseEntity.getPrice())
                .classroom(courseEntity.getClassroom())
                .semester(courseEntity.getSemester())
                .year(courseEntity.getYear())
                .teamsLink(courseEntity.getTeamsLink())
                .lastRegDay(courseEntity.getLastRegDay())
                .instructors(mapInstructors(courseEntity))
                .students(mapStudents(courseEntity))
                .build();
    }

    private List<AdminInstructorResponseDto> mapInstructors(CourseEntity courseEntity) {
        return instructorCoursesRepo.findAll(Example.of(InstructorCoursesEntity.builder()
                        .course(CourseEntity.builder().active(true).id(courseEntity.getId()).build()).build())).stream()
                .map(r -> adminInstructorMapper.mapEntityToDto(r.getInstructor()))
                .collect(Collectors.toList());
    }

    private List<AdminStudentResponseDto> mapStudents(CourseEntity courseEntity) {
        return studentCoursesRepo.findAll(Example.of(StudentCoursesEntity.builder()
                        .course(CourseEntity.builder().active(true).id(courseEntity.getId()).build()).build())).stream()
                .map(r -> {
                    AdminStudentResponseDto adminStudentResponseDto = adminStudentMapper.mapEntityToDto(r.getStudent());
                    adminStudentResponseDto.setPaid(r.getPaid());
                    return adminStudentResponseDto;
                })
                .collect(Collectors.toList());
    }
}
