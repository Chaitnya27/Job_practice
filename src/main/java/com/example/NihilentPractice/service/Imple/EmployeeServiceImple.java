package com.example.NihilentPractice.service.Imple;

import com.example.NihilentPractice.Dto.EmployeeRequestDto;
import com.example.NihilentPractice.Dto.EmployeeResponseDto;
import com.example.NihilentPractice.entity.Department;
import com.example.NihilentPractice.entity.Employee;
import com.example.NihilentPractice.exception.ResourceNotFoundException;
import com.example.NihilentPractice.repository.DepartmentRepository;
import com.example.NihilentPractice.repository.EmployeeRepository;
import com.example.NihilentPractice.service.DepartmentService;
import com.example.NihilentPractice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImple  implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final DepartmentRepository departmentRepository;


    @Override
    public List<EmployeeResponseDto> getAllEmployee() {
        List<Employee> employee = employeeRepository.findAll();
        List<EmployeeResponseDto> employeeRequestDtos = employee.stream()
                .map(employee1 ->{
                    EmployeeResponseDto responseDto=
                    modelMapper.map(employee1, EmployeeResponseDto.class);

                    if (employee1.getDepartment() != null) {
                        responseDto.setDepartId(employee1.getDepartment().getId());
                    }
                    return responseDto;
                })
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

        if(employee.getDepartment() != null){
            employeeRequestDto.setDepartId(employee.getDepartment().getId());
        }

        return employeeRequestDto;
    }

    @Override
    public List<EmployeeResponseDto> getEmployeeByDepartmentId(Long departId) {
       departmentRepository.findById(departId)
                .orElseThrow(()-> new ResourceNotFoundException("Department not found id "+ departId));

       List<Employee> employee=  employeeRepository.findByDepartmentId(departId);


        List<EmployeeResponseDto> employeeResponseDtos = employee.stream()
                .map(
                        employee1 -> {
                    EmployeeResponseDto dto =modelMapper.map(employee1, EmployeeResponseDto.class);
                dto.setDepartId(employee1.getDepartment().getId());
                return dto;
                })

                .collect(Collectors.toList());

        return employeeResponseDtos;

    }

    @Override
    public EmployeeRequestDto createEmployee(EmployeeRequestDto employeeRequestDto) {

        // STEP 1: Check whether email already exists
        if(employeeRepository.existsByEmail(employeeRequestDto.getEmail())){
            throw  new ResourceNotFoundException("email already exist");
        }

        // STEP 2: Check whether phone number already exists
        if(employeeRepository.existsByPhoneNumber(employeeRequestDto.getPhoneNumber())){
            throw  new ResourceNotFoundException("Phone Number already exist");
        }

        // STEP 3: Convert EmployeeRequestDto into Employee entity
        Employee employee = modelMapper.map(employeeRequestDto,Employee.class);

        // STEP 4: Fetch Department using departId from request DTO
        Department department =departmentRepository.findById(
                employeeRequestDto.getDepartId())
                .orElseThrow(()-> new ResourceNotFoundException("Department not found with id :"+ employeeRequestDto.getDepartId()));

        // STEP 5: Assign Department object to Employee
        employee.setDepartment(department);


        // STEP 6: Save Employee in database
        Employee savedEmployee = employeeRepository.save(employee);


        // STEP 7: Convert saved Employee entity into Response DTO
        EmployeeRequestDto employeeRequestDto1 = modelMapper.map(savedEmployee, EmployeeRequestDto.class);

        // STEP 8: Manually set Department ID in response
        employeeRequestDto1.setDepartId(savedEmployee.getDepartment().getId());
        return employeeRequestDto1;

    }

    @Override
    public EmployeeRequestDto updateEmployee(int empId, EmployeeRequestDto employeeRequestDto) {

        // Step 1: Find existing employee
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not found with id " + empId));

        //Step 2 :- find or duplicate email and Phone number
        if(employeeRepository.existsByEmail(employeeRequestDto.getEmail())){
            throw  new ResourceNotFoundException("email already exist");
        }

        if(employeeRepository.existsByPhoneNumber(employeeRequestDto.getPhoneNumber())){
            throw  new ResourceNotFoundException("Phone Number already exist");
        }

        // Step 3: Map DTO data into existing Entity
        modelMapper.map(employeeRequestDto,employee);

        //step 4: Find Department using departId
        Department department = departmentRepository.findById(employeeRequestDto.getDepartId())
                .orElseThrow(()-> new ResourceNotFoundException("Department not found with id :"+ employeeRequestDto.getDepartId()));

        //step 5: Assign Department object to Employee
        employee.setDepartment(department);


        //  Step 6: Save updated employee
        Employee updatedEmployee1 = employeeRepository.save(employee);

        // Step 7: Convert Entity into DTO
        EmployeeRequestDto dto =
                modelMapper.map(updatedEmployee1, EmployeeRequestDto.class);

        //step 8: Manually set Department ID in response
        dto.setDepartId(updatedEmployee1.getDepartment().getId());

        return dto;
    }

    @Override
    public EmployeeRequestDto patchEmployee(int empId, Map<String, Object> fields) {

        // Step 1: Find employee
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not found with id " + empId));

        // Step 2: Update fields dynamically
        fields.forEach((key, value) -> {

            if (key.equals("departId")) {

                Long departmentId = ((Number) value).longValue();

                Department department = departmentRepository.findById(departmentId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Department not found with id " + departmentId));

                employee.setDepartment(department);
                return;
            }

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
        EmployeeRequestDto dto = modelMapper.map(updatedEmployee, EmployeeRequestDto.class);

        if (updatedEmployee.getDepartment() != null) {
            dto.setDepartId(updatedEmployee.getDepartment().getId());
        }

        return dto;


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
