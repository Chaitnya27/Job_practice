package com.example.NihilentPractice.Dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequestDto {



    @NotBlank(message = "Can't be blank")
    @Size(min = 2,max =11 , message = "Name must be between 2 to 11 Character")
    private String name;

    @NotBlank(message = "Can't be blank")
    @Email(message = "Invalid email format")
    private String email;

    @Positive(message = "Salary Must be positive")
    @Min(value = 10000,message = "Must be greater than 10000")
    private double salary;

    @NotBlank(message = "Can't be blank")
    @Size(max=50,message = "Must not exceed 50 characters")
    private String address;

    @NotNull(message = "Cant be null")
    @Positive(message = "Department ID must be positive")
    private Long departId;

    @Positive(message = "Age Must be positive")
    @Min(value = 20,message = "Must be greater than 20")
    private int age;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must contain exactly 10 digits"
    )
    private String phoneNumber;


}
