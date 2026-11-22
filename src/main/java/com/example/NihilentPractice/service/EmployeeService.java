package com.example.NihilentPractice.service;


import com.example.NihilentPractice.Dto.EmployeeRequestDto;
import com.example.NihilentPractice.Dto.EmployeeResponseDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

    EmployeeRequestDto createEmployee(EmployeeRequestDto employeeRequestDto);

    boolean deleteEmployee(int empId);

    EmployeeResponseDto getEmployeeById(int empId);

    List<EmployeeResponseDto> getAllEmployee();

    EmployeeRequestDto updateEmployee(int empId, @Valid EmployeeRequestDto employeeRequestDto);

    EmployeeRequestDto patchEmployee(int empId, @Valid Map<String, Object> fields);
}
