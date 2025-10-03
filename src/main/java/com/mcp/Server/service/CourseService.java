package com.mcp.Server.service;

import com.mcp.Server.model.Course;
import com.mcp.Server.repository.CourseRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Tool(
            name = "addNewCourse",
            description = "Add a new course with the given title and description"
    )
    @Transactional
    public Course addNewCourse(
            @ToolParam(description = "The title of the course")
            @NotBlank(message = "Course title is required")
                String courseTitle,
            @ToolParam(description = "The description of the course")
            @NotBlank(message = "Course description is required")
                String courseDescription
    ) {

        Course course = Course.builder()
                .courseTitle(courseTitle)
                .courseDescription(courseDescription.trim())
                .build();

        return courseRepository.save(course);

    }

    @Tool(
            name = "getAllCourses",
            description = "Get the list of all Currently Available Courses."
    )
    public List<Course> getAllCourses() {

        return courseRepository.findAll();

    }

    @Tool(
            name = "getCoursesByTitle",
            description = "Get the list of all the available courses whose courseTitle matches with the provided courseTitle."
    )
    public List<Course> getCoursesByTitle(
            @ToolParam(description = "The small title of the course")
            @NotBlank(message = "Course title is required")
                String courseTitle
    ) {

        return courseRepository.findByCourseTitleContainingIgnoreCase(courseTitle);

    }

}
