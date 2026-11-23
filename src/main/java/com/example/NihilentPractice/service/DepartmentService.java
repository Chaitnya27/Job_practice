package com.example.NihilentPractice.service;

import com.example.NihilentPractice.Dto.DepartmentRequestDto;
import com.example.NihilentPractice.Dto.DepartmentResponseDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface DepartmentService {

    DepartmentRequestDto createDepartment(DepartmentRequestDto departmentRequestDto);

    DepartmentResponseDto getDepartmentById(Long id);

    List<DepartmentResponseDto> getAllDepartment();

    DepartmentRequestDto updateDepartment(Long id, DepartmentRequestDto departmentRequestDto);

    boolean deleteEmployee(Long id);

    DepartmentRequestDto patchDepartment(Long id, Map<String, Object> fields);
}
