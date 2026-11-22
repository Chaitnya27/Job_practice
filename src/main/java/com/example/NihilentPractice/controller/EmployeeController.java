package com.example.NihilentPractice.controller;

import com.example.NihilentPractice.Dto.EmployeeRequestDto;
import com.example.NihilentPractice.Dto.EmployeeResponseDto;
import com.example.NihilentPractice.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployee(){
       List<EmployeeResponseDto> employeeRequestDtos =  employeeService.getAllEmployee();
       return  ResponseEntity.ok(employeeRequestDtos);
    }

    @GetMapping("/{empId}")

    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable int empId){
        EmployeeResponseDto savedEmployee=  employeeService.getEmployeeById(empId);
        return ResponseEntity.ok(savedEmployee);
    }

    @PostMapping
    public ResponseEntity<EmployeeRequestDto> createEmployee(@Valid @RequestBody EmployeeRequestDto employeeRequestDto){

        EmployeeRequestDto savedEmployee =  employeeService.createEmployee(employeeRequestDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/{empId}")
    public ResponseEntity<EmployeeRequestDto> updateEmployee(@PathVariable int empId, @Valid  @RequestBody EmployeeRequestDto employeeRequestDto){
        EmployeeRequestDto employeeRequestDto1 = employeeService.updateEmployee(empId, employeeRequestDto);
        return ResponseEntity.ok(employeeRequestDto1);
    }


    @PatchMapping("/{empId}")
    public ResponseEntity<EmployeeRequestDto> patchEmployee(
            @PathVariable int empId,
           @Valid @RequestBody Map<String, Object> fields) {


        EmployeeRequestDto updatedEmployee = employeeService.patchEmployee(empId, fields);

        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{empId}")

    public ResponseEntity<Void> deleteEmployee(@PathVariable int empId){
        boolean exist =  employeeService.deleteEmployee(empId);
        if(!exist){
            return ResponseEntity.notFound().build();
        }


        return  ResponseEntity.noContent().build();

    }

}
