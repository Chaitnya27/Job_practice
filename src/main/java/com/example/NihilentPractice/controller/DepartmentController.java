package com.example.NihilentPractice.controller;


import com.example.NihilentPractice.Dto.DepartmentRequestDto;
import com.example.NihilentPractice.Dto.DepartmentResponseDto;
import com.example.NihilentPractice.Dto.EmployeeRequestDto;
import com.example.NihilentPractice.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {


    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDto>> getAllDepartment(){
        List<DepartmentResponseDto> savedDepartment = departmentService.getAllDepartment();
        return ResponseEntity.ok(savedDepartment);
    }


    @GetMapping("/{id}")

    public ResponseEntity<DepartmentResponseDto> getDepartmentById(@PathVariable Long id){
        DepartmentResponseDto savedDepartment = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(savedDepartment);
    }

    @PostMapping

    public ResponseEntity<DepartmentRequestDto> createDepartment(@Valid  @RequestBody DepartmentRequestDto departmentRequestDto){
        DepartmentRequestDto savedDepartmentRequestDto = departmentService. createDepartment(departmentRequestDto);
        return new ResponseEntity<>(savedDepartmentRequestDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")

    public ResponseEntity<DepartmentRequestDto> updateDepartment(@PathVariable Long id ,@Valid @RequestBody DepartmentRequestDto departmentRequestDto){
        DepartmentRequestDto savedDepartmentRequestDto = departmentService. updateDepartment(id,departmentRequestDto);
        return  ResponseEntity.ok(savedDepartmentRequestDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DepartmentRequestDto> patchDepartment(
            @PathVariable Long id,
            @Valid @RequestBody Map<String, Object> fields) {


        DepartmentRequestDto updatedDepartment = departmentService.patchDepartment(id, fields);

        return ResponseEntity.ok(updatedDepartment);
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){
        boolean exist =  departmentService.deleteEmployee(id);
        if(!exist){
            return ResponseEntity.notFound().build();
        }


        return  ResponseEntity.noContent().build();

    }

}
