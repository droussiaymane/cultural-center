package com.ju.islamicculturalcenter.restcontrollers.instructor;

import com.ju.islamicculturalcenter.dto.request.instructor.material.InstructorMaterialRequestDto;
import com.ju.islamicculturalcenter.dto.request.instructor.material.InstructorMaterialUpdateRequestDto;
import com.ju.islamicculturalcenter.dto.response.CODE;
import com.ju.islamicculturalcenter.dto.response.Response;
import com.ju.islamicculturalcenter.dto.response.instructor.material.InstructorMaterialResponseDto;
import com.ju.islamicculturalcenter.service.iservice.instructor.MaterialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/instructor/materials")
public class InstructorMaterialController {
    private final MaterialService materialService;

    public InstructorMaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping
    public ResponseEntity<Response<InstructorMaterialResponseDto>> save(@RequestBody InstructorMaterialRequestDto requestDto) {
        Response<InstructorMaterialResponseDto> response = Response.<InstructorMaterialResponseDto>builder()
                .success(true)
                .data(materialService.save(requestDto))
                .code(CODE.CREATED.getId())
                .message(CODE.CREATED.name())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Response<List<InstructorMaterialResponseDto>>> getMaterialsByCourseId(@PathVariable Long courseId) {
        Response<List<InstructorMaterialResponseDto>> response = Response.<List<InstructorMaterialResponseDto>>builder()
                .success(true)
                .data(materialService.viewCourseMaterials(courseId))
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteMaterial(@PathVariable Long id) {
        materialService.deleteById(id);
        Response<Void> response = Response.<Void>builder()
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<InstructorMaterialResponseDto>> updateMaterial(@PathVariable Long id, @RequestBody InstructorMaterialUpdateRequestDto requestDto) {
        Response<InstructorMaterialResponseDto> response = Response.<InstructorMaterialResponseDto>builder()
                .data(materialService.update(id, requestDto))
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
