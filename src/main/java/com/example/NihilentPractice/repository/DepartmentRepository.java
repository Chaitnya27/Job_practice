package com.example.NihilentPractice.repository;

import com.example.NihilentPractice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {

    boolean existsByDepartName(String departName);
}
