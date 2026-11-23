package com.example.NihilentPractice.service;


import com.example.NihilentPractice.Dto.EmployeeRequestDto;
import com.example.NihilentPractice.Dto.EmployeeResponseDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface EmployeeService {


    List<EmployeeResponseDto> getAllEmployee();

    EmployeeResponseDto getEmployeeById(int empId);

    EmployeeRequestDto createEmployee(EmployeeRequestDto employeeRequestDto);

    EmployeeRequestDto updateEmployee(int empId, @Valid EmployeeRequestDto employeeRequestDto);

    EmployeeRequestDto patchEmployee(int empId, @Valid Map<String, Object> fields);

    boolean deleteEmployee(int empId);
}
