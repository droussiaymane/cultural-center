package com.ju.islamicculturalcenter.restcontrollers.instructor;

import com.ju.islamicculturalcenter.dto.request.instructor.profile.InstructorUpdateProfileDto;
import com.ju.islamicculturalcenter.dto.request.instructor.profile.InstructorUpdatePassword;
import com.ju.islamicculturalcenter.dto.response.CODE;
import com.ju.islamicculturalcenter.dto.response.Response;
import com.ju.islamicculturalcenter.dto.response.instructor.course.InstructorCourseResponseDto;
import com.ju.islamicculturalcenter.dto.response.instructor.profile.InstructorResponseDto;
import com.ju.islamicculturalcenter.service.auth.UserDetailsUtil;
import com.ju.islamicculturalcenter.service.iservice.instructor.InstructorCoursesService;
import com.ju.islamicculturalcenter.service.iservice.instructor.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/instructor/instructors")
public class InstructorRestController {

    private final InstructorService instructorService;
    private final InstructorCoursesService instructorCoursesService;

    public InstructorRestController(InstructorService instructorService, InstructorCoursesService instructorCoursesService) {
        this.instructorService = instructorService;
        this.instructorCoursesService = instructorCoursesService;
    }

    @GetMapping("/profile")
    public ResponseEntity<Response<InstructorResponseDto>> viewProfile(){
        Response<InstructorResponseDto> response= Response.<InstructorResponseDto>builder()
                .data(instructorService.viewProfile())
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/password")
    public ResponseEntity<Response<Void>> updateOwnPassword(@RequestBody InstructorUpdatePassword request) {
        instructorService.updatePassword(request);

        Response<Void> response= Response.<Void>builder()
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<Response<InstructorResponseDto>> updateProfile(@RequestBody InstructorUpdateProfileDto request) {

        Response<InstructorResponseDto> response= Response.<InstructorResponseDto>builder()
                .data(instructorService.update(UserDetailsUtil.userDetails().getId(), request))
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/my-courses")
    public ResponseEntity<Response<List<InstructorCourseResponseDto>>> viewMyCourses(){
        Response<List<InstructorCourseResponseDto>> response= Response.<List<InstructorCourseResponseDto>>builder()
                .data(instructorCoursesService.myCourses())
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/course/search")
    public ResponseEntity<Response<List<InstructorCourseResponseDto>>> searchCourse(@RequestParam String keyword){
        Response<List<InstructorCourseResponseDto>> response= Response.<List<InstructorCourseResponseDto>>builder()
                .data(instructorCoursesService.searchCourseByName(keyword))
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}
