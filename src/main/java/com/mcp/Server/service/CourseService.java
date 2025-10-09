package com.mcp.Server.service;

import com.mcp.Server.model.Course;
import com.mcp.Server.repository.CourseRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @Tool(
            name = "removeCourseById",
            description = "Remove the course whose courseId matches the provided courseId. If no course is found, return 'Course doesn't exist'. Requires userConfirmed=true to proceed deletion. If user confirms the deletion then proceed with {userConfirmed}=true"
    )
    public String removeCourseById(
            @ToolParam(description = "The ID of the course")
            @NotNull(message = "courseId is required")
            Long courseId,

            @ToolParam(description = "User confirmation to remove the course")
            @NotNull(message = "User confirmation is required")
            boolean userConfirmed
    ) {

        if (!userConfirmed) {
            return "Are you sure you want to remove the course with courseId: " + courseId + "? Please confirm by saying Yes.";
        }

        Course courseToDelete = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course with courseId " + courseId + " doesn't exist."));

        courseRepository.delete(courseToDelete);

        return "Course with courseId: " + courseId + " removed successfully.";
    }


    @Tool(
            name = "updateCourseByCourseId",
            description = "Update the title and description of a course based on its courseId. Requires userConfirmed=true to proceed updating course. If user confirms the updating course then proceed with {userConfirmed}=true"
    )
    public Object updateCourseByCourseId(
            @ToolParam(description = "The ID of the course")
            @NotNull(message = "courseId is required")
            Long courseId,

            @ToolParam(description = "The small title of the course")
            @NotBlank(message = "Course title is required")
            String courseTitle,

            @ToolParam(description = "The description of the course")
            @NotBlank(message = "Course description is required")
            String courseDescription,

            @ToolParam(description = "User confirmation to update the course")
            @NotNull(message = "User confirmation is required")
            boolean userConfirmed
    ) {

        if (!userConfirmed) {
            return "Are you sure you want to update the course with courseId: " + courseId + "? Please confirm by saying Yes.";
        }

        Course courseToUpdate = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course with courseId " + courseId + " doesn't exist."));

        courseToUpdate.setCourseTitle(courseTitle.trim());
        courseToUpdate.setCourseDescription(courseDescription.trim());

        return courseRepository.save(courseToUpdate);
    }

}
