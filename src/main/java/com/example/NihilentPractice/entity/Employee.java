package com.example.NihilentPractice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="employee")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int empId;

    private String name;

    @Column(unique = true)
    private String email;

    private int age;

    private double salary;

    private String address;

    private Long departId;

    @Column(unique = true, nullable = false)
    private String phoneNumber;
}
