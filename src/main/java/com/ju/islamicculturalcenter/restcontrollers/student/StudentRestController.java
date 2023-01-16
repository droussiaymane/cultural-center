package com.ju.islamicculturalcenter.restcontrollers.student;

import java.util.List;

import com.ju.islamicculturalcenter.dto.request.student.StudentRegistrationRequestDto;
import com.ju.islamicculturalcenter.dto.response.admin.AdminStudentResponseDto;
import com.ju.islamicculturalcenter.service.impl.student.StudentServiceImpl;
import org.springframework.web.bind.annotation.*;

import com.ju.islamicculturalcenter.service.impl.admin.AdminStudentServiceImp;

@RestController
@RequestMapping("/students")
public class StudentRestController {

    private AdminStudentServiceImp adminStudentServiceImp;

    private StudentServiceImpl studentServiceImpl;

    public StudentRestController(StudentServiceImpl studentServiceImpl, AdminStudentServiceImp adminStudentServiceImp) {
        this.studentServiceImpl = studentServiceImpl;
        this.adminStudentServiceImp = adminStudentServiceImp;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<AdminStudentResponseDto> getStudents(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                     @RequestParam(required = false, defaultValue = "20") Integer size) {
        return adminStudentServiceImp.findAllByActive(page, size, true).getData();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createDto(@RequestBody StudentRegistrationRequestDto studentRegistrationRequestDto) {
        studentServiceImpl.save(studentRegistrationRequestDto);
    }


}
