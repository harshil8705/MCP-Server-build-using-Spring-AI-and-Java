package com.mcp.Server.service;

import com.mcp.Server.model.Course;
import com.mcp.Server.repository.CourseRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Tool(name = "addNewCourse", description = "Add a New Course")
    @Transactional
    public Course addNewCourse(@Valid Course course) {

        return courseRepository.save(course);

    }

    @Tool(name = "getAllCourses", description = "Get all the Available Courses.")
    public List<Course> getAllCourses() {

        return courseRepository.findAll();

    }

    @Tool(name = "getCoursesByTitle", description = "Get courses based on the provided courseTitle")
    public List<Course> getCoursesByTitle(@NotBlank(message = "Course title is required") String courseTitle) {

        return courseRepository.findByCourseTitleContainingIgnoreCase(courseTitle);

    }

}
