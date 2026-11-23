package com.example.NihilentPractice.service.Imple;

import com.example.NihilentPractice.Dto.DepartmentRequestDto;
import com.example.NihilentPractice.Dto.DepartmentResponseDto;
import com.example.NihilentPractice.Dto.EmployeeRequestDto;
import com.example.NihilentPractice.Dto.EmployeeResponseDto;
import com.example.NihilentPractice.entity.Department;
import com.example.NihilentPractice.entity.Employee;
import com.example.NihilentPractice.exception.ResourceNotFoundException;
import com.example.NihilentPractice.repository.DepartmentRepository;
import com.example.NihilentPractice.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImple  implements DepartmentService {
    private final ModelMapper modelMapper;

    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentResponseDto> getAllDepartment() {
        List<Department> departments = departmentRepository.findAll();

       return  departments.stream()
                .map(department -> modelMapper.map(department, DepartmentResponseDto.class))
                .collect(Collectors.toList());

    }



    @Override
    public DepartmentResponseDto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id " + id));

       return modelMapper.map(department, DepartmentResponseDto.class);
    }




    @Override
    public DepartmentRequestDto createDepartment(DepartmentRequestDto departmentRequestDto) {
        Department department = modelMapper.map(departmentRequestDto,Department.class);

        if(departmentRepository.existsByDepartName(department.getDepartName())){
            throw new ResourceNotFoundException("Department name already exists");
        }
        Department savedDepartment = departmentRepository.save(department);

        return modelMapper.map(savedDepartment, DepartmentRequestDto.class);
    }

    @Override
    public DepartmentRequestDto updateDepartment(Long id, DepartmentRequestDto departmentRequestDto) {
       Department department = departmentRepository.findById(id)
               .orElseThrow(()-> new RuntimeException("Department not found with id " + id));

        modelMapper.map(departmentRequestDto,department);

        //  Step 3: Save updated employee
        Department updatedDepartment= departmentRepository.save(department);

        // Step 4: Convert Entity into DTO
        return modelMapper.map(updatedDepartment, DepartmentRequestDto.class);
    }

    @Override
    public boolean deleteEmployee(Long id) {
        boolean exist = departmentNotExist(id);
        if(!exist){
            throw  new ResourceNotFoundException("Department not found with id " + id);
        }
        departmentRepository.deleteById(id);

        return true;
    }

    @Override
    public DepartmentRequestDto patchDepartment(Long id, Map<String, Object> fields) {
       Department department = departmentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Department not found with id " + id));

        // Step 2: Update fields dynamically
        fields.forEach((key, value) -> {

            Field field = ReflectionUtils.findField(Department.class, key);

            if (field == null || key.equals("id")) {
                throw new RuntimeException("Invalid field: " + key);
            }

            field.setAccessible(true);
            ReflectionUtils.setField(field, department, value);
        });

        // Step 3: Save employee
        Department updatedDepartment = departmentRepository.save(department);

        // Step 4: Convert Entity into DTO
        return modelMapper.map(updatedDepartment, DepartmentRequestDto.class);
    }

    public boolean departmentNotExist(Long id){

        return departmentRepository.existsById(id);
    }


}
