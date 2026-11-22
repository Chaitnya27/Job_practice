package com.example.NihilentPractice.service.Imple;

import com.example.NihilentPractice.Dto.EmployeeRequestDto;
import com.example.NihilentPractice.Dto.EmployeeResponseDto;
import com.example.NihilentPractice.entity.Employee;
import com.example.NihilentPractice.exception.ResourceNotFoundException;
import com.example.NihilentPractice.repository.EmployeeRepository;
import com.example.NihilentPractice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImple  implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<EmployeeResponseDto> getAllEmployee() {
        List<Employee> employee = employeeRepository.findAll();
        List<EmployeeResponseDto> employeeRequestDtos = employee.stream()
                .map(employee1 -> modelMapper.map(employee1, EmployeeResponseDto.class))
                .collect(Collectors.toList());

        return employeeRequestDtos;

    }



    @Override
    public EmployeeResponseDto getEmployeeById(int empId) {

        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id " + empId));

        EmployeeResponseDto employeeRequestDto =
                modelMapper.map(employee, EmployeeResponseDto.class);

        return employeeRequestDto;
    }

    @Override
    public EmployeeRequestDto createEmployee(EmployeeRequestDto employeeRequestDto) {

        // convert dto to  entity
        Employee employee = modelMapper.map(employeeRequestDto,Employee.class);

        //save entity to database

        if(employeeRepository.existsByEmail(employeeRequestDto.getEmail())){
            throw  new RuntimeException("email already exist");
        }
        Employee savedEmployee = employeeRepository.save(employee);

        //convert entity to dto

        EmployeeRequestDto employeeRequestDto1 = modelMapper.map(savedEmployee, EmployeeRequestDto.class);

        return employeeRequestDto1;

    }

    @Override
    public EmployeeRequestDto updateEmployee(int empId, EmployeeRequestDto employeeRequestDto) {

        // Step 1: Find existing employee
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not found with id " + empId));



        // Step 2: Map DTO data into existing Entity
        modelMapper.map(employeeRequestDto,employee);

        //  Step 3: Save updated employee
        Employee updatedEmployee1 = employeeRepository.save(employee);

        // Step 4: Convert Entity into DTO
        return modelMapper.map(updatedEmployee1, EmployeeRequestDto.class);
    }

    @Override
    public EmployeeRequestDto patchEmployee(int empId, Map<String, Object> fields) {

        // Step 1: Find employee
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not found with id " + empId));

        // Step 2: Update fields dynamically
        fields.forEach((key, value) -> {

            Field field = ReflectionUtils.findField(Employee.class, key);

            if (field == null || key.equals("empId")) {
                throw new RuntimeException("Invalid field: " + key);
            }

            field.setAccessible(true);
            ReflectionUtils.setField(field, employee, value);
        });

        // Step 3: Save employee
        Employee updatedEmployee = employeeRepository.save(employee);

        // Step 4: Convert Entity into DTO
        return modelMapper.map(updatedEmployee, EmployeeRequestDto.class);


    }

    @Override
    public boolean deleteEmployee(int empId) {
        boolean exist = EmployeeNotExist(empId);
        if(!exist){
          throw  new ResourceNotFoundException("Employee not found with id " + empId);
        }
        employeeRepository.deleteById(empId);

        return true;

    }





    public boolean EmployeeNotExist(int empId){
        return employeeRepository.existsById(empId);
    }
}
