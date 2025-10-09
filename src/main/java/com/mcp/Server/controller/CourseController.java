package com.mcp.Server.controller;

import com.mcp.Server.model.Course;
import com.mcp.Server.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/add/course")
    public ResponseEntity<Course> addNewCourse(@RequestParam(name = "courseTitle") String courseTitle,
                                               @RequestParam(name = "courseDescription") String courseDescription) {

        return new ResponseEntity<>(courseService.addNewCourse(courseTitle, courseDescription), HttpStatus.OK);

    }

    @GetMapping("/get/courses")
    public ResponseEntity<List<Course>> getAllCourses() {

        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);

    }

    @GetMapping("/search/courses")
    public ResponseEntity<List<Course>> getCoursesByTitle(@RequestParam(value = "title") String courseTitle) {

        return new ResponseEntity<>(courseService.getCoursesByTitle(courseTitle), HttpStatus.OK);

    }

    @DeleteMapping("/remove/course/{courseId}")
    public ResponseEntity<String> removeCourseById(@PathVariable Long courseId, boolean userConfirmed) {

        return new ResponseEntity<>(courseService.removeCourseById(courseId, userConfirmed), HttpStatus.OK);

    }

    @PutMapping("/update/course/{courseId}")
    public ResponseEntity<?> updateCourseByCourseId(@PathVariable Long courseId, String courseTitle, String courseDescription, boolean userConfirmed) {

        return new ResponseEntity<>(courseService.updateCourseByCourseId(courseId, courseTitle, courseDescription, userConfirmed), HttpStatus.OK);

    }

}
