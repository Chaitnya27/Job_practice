package com.example.NihilentPractice.Dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DepartmentRequestDto {

    @NotBlank(message = "Department name is required")
    @Size(min = 2, message = "Department name must contain at least 2 characters")
    private String departName;

    @NotBlank(message = "Description is required")
    @Size(min = 2, max = 50, message = "Description must be between 2 and 50 characters")
    private String description;
}
