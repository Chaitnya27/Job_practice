package com.example.NihilentPractice.repository;

import com.example.NihilentPractice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
