package com.example.NihilentPractice.repository;

import com.example.NihilentPractice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    List<Employee> findByDepartmentId(Long departId);
}
