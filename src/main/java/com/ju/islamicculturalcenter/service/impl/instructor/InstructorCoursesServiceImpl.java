package com.ju.islamicculturalcenter.service.impl.instructor;

import com.ju.islamicculturalcenter.dto.response.instructor.course.InstructorCourseResponseDto;
import com.ju.islamicculturalcenter.dto.response.instructor.student.InstructorStudentResponseDto;
import com.ju.islamicculturalcenter.entity.CourseEntity;
import com.ju.islamicculturalcenter.entity.InstructorCoursesEntity;
import com.ju.islamicculturalcenter.entity.InstructorEntity;
import com.ju.islamicculturalcenter.entity.StudentCoursesEntity;
import com.ju.islamicculturalcenter.entity.UserEntity;
import com.ju.islamicculturalcenter.exceptions.ValidationException;
import com.ju.islamicculturalcenter.repos.InstructorCoursesRepo;
import com.ju.islamicculturalcenter.repos.StudentCoursesRepo;
import com.ju.islamicculturalcenter.service.auth.UserDetailsUtil;
import com.ju.islamicculturalcenter.service.iservice.instructor.InstructorCoursesService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class InstructorCoursesServiceImpl implements InstructorCoursesService {

    private final InstructorCoursesRepo instructorCoursesRepo;
    private final StudentCoursesRepo studentCoursesRepo;

    public InstructorCoursesServiceImpl(InstructorCoursesRepo instructorCoursesRepo, StudentCoursesRepo studentCoursesRepo) {
        this.instructorCoursesRepo = instructorCoursesRepo;
        this.studentCoursesRepo = studentCoursesRepo;
    }

    @Override
    public List<InstructorCourseResponseDto> myCourses() {

        List<InstructorCoursesEntity> entities = instructorCoursesRepo.findAll(Example.of(InstructorCoursesEntity.builder()
                .active(true)
                .instructor(InstructorEntity.builder().user(UserEntity.builder().id(UserDetailsUtil.userDetails().getId()).build()).build())
                .build()));

        return entities.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<InstructorCourseResponseDto> searchCourseByName(String name) {
        if (isNull(name) || name.length() < 3)
            throw new ValidationException("Name cannot be empty or less than 3 characters");

        return instructorCoursesRepo.findAllByInstructor_User_IdAndCourse_NameLike(UserDetailsUtil.userDetails().getId(), name).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    private InstructorCourseResponseDto mapEntityToDto(InstructorCoursesEntity entity) {
        return InstructorCourseResponseDto.builder()
                .id(entity.getId())
                .name(entity.getCourse().getName())
                .description(entity.getCourse().getDescription())
                .category(entity.getCourse().getCategory())
                .classroom(entity.getCourse().getClassroom())
                .startDate(entity.getCourse().getStartDate())
                .endDate(entity.getCourse().getEndDate())
                .duration(entity.getCourse().getDuration())
                .lectureTime(entity.getCourse().getLectureTime())
                .daysOfWeek(entity.getCourse().getDaysOfWeek())
                .isPreRecorded(entity.getCourse().getIsPreRecorded())
                .isOnline(entity.getCourse().getIsOnline())
                .year(entity.getCourse().getYear())
                .maxParticipants(entity.getCourse().getMaxParticipants())
                .semester(entity.getCourse().getSemester())
                .lastRegDay(entity.getCourse().getLastRegDay())
                .teamsLink(entity.getCourse().getTeamsLink())
                .students(getStudentsByCourseId(entity.getCourse().getId()))
                .build();
    }

    private List<InstructorStudentResponseDto> getStudentsByCourseId(Long courseId) {
        return studentCoursesRepo.findAll(Example.of(StudentCoursesEntity.builder()
                        .active(true)
                        .course(CourseEntity.builder().id(courseId).build())
                        .build())).stream()
                .map(r -> InstructorStudentResponseDto.builder()
                        .id(r.getId())
                        .firstName(r.getStudent().getUser().getFirstName())
                        .lastName(r.getStudent().getUser().getLastName())
                        .email(r.getStudent().getUser().getEmail())
                        .phoneNumber(r.getStudent().getUser().getPhoneNumber())
                        .facebookUrl(r.getStudent().getUser().getFacebookUrl())
                        .dateOfBirth(r.getStudent().getDateOfBirth())
                        .build())
                .collect(Collectors.toList());
    }
}
