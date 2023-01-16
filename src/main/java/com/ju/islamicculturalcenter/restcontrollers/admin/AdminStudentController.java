package com.ju.islamicculturalcenter.restcontrollers.admin;

import com.ju.islamicculturalcenter.dto.request.admin.student.AdminResetStudentPasswordRequestDto;
import com.ju.islamicculturalcenter.dto.request.admin.student.AdminStudentRequestDto;
import com.ju.islamicculturalcenter.dto.request.admin.student.AdminUpdateStudentRequestDto;
import com.ju.islamicculturalcenter.dto.response.CODE;
import com.ju.islamicculturalcenter.dto.response.Response;
import com.ju.islamicculturalcenter.dto.response.ResponseList;
import com.ju.islamicculturalcenter.dto.response.admin.AdminStudentResponseDto;
import com.ju.islamicculturalcenter.service.iservice.admin.AdminStudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/students")
public class AdminStudentController {

    private final AdminStudentService adminStudentService;

    public AdminStudentController(AdminStudentService adminStudentService) {
        this.adminStudentService = adminStudentService;
    }

    @PostMapping
    public ResponseEntity<Response<AdminStudentResponseDto>> createInstructor(@RequestBody AdminStudentRequestDto requestDto) {
        Response<AdminStudentResponseDto> response = Response.<AdminStudentResponseDto>builder()
                .data(adminStudentService.save(requestDto))
                .code(CODE.CREATED.getId())
                .message(CODE.CREATED.name())
                .success(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<List<AdminStudentResponseDto>>> listInstructors(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                                                   @RequestParam(required = false, defaultValue = "20") Integer size) {
        ResponseList<AdminStudentResponseDto> active = adminStudentService.findAllByActive(page, size, true);
        Response<List<AdminStudentResponseDto>> response = Response.<List<AdminStudentResponseDto>>builder()
                .data(active.getData())
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .allRecords(active.getTotalElements())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<AdminStudentResponseDto>> viewInstructorProfile(@PathVariable Long id) {
        Response<AdminStudentResponseDto> response = Response.<AdminStudentResponseDto>builder()
                .data(adminStudentService.findById(id, true))
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<AdminStudentResponseDto>> updateInstructor(@PathVariable Long id, @RequestBody AdminUpdateStudentRequestDto updateDto){
        Response<AdminStudentResponseDto> response = Response.<AdminStudentResponseDto>builder()
                .data(adminStudentService.update(id, updateDto))
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteInstructor(@PathVariable Long id) {
        adminStudentService.deleteById(id);
        Response<Void> response = Response.<Void>builder()
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<Response<Void>> changeInstructorPassword(@RequestBody AdminResetStudentPasswordRequestDto requestDto) {
        adminStudentService.resetPassword(requestDto);
        Response<Void> response = Response.<Void>builder()
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Response<List<AdminStudentResponseDto>>> searchByName(@RequestParam String keyword){
        Response<List<AdminStudentResponseDto>> response = Response.<List<AdminStudentResponseDto>>builder()
                .data(adminStudentService.searchStudentByName(keyword))
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
