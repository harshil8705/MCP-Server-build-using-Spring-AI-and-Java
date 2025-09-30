package com.mcp.Server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @NotBlank(message = "Course title is required")
    @Size(max = 150, message = "Course title must be at most 150 characters")
    @Column(nullable = false, length = 150)
    private String courseTitle;

    @NotBlank(message = "Course description is required")
    @Size(max = 500, message = "Course description must be at most 500 characters")
    @Column(nullable = false, length = 500)
    private String courseDescription;

}
