package srau.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import srau.api.domain.Course;
import srau.api.mapstruct.dto.CourseGetDto;
import srau.api.mapstruct.dto.CoursePostDto;
import srau.api.mapstruct.mapper.CourseMapper;
import srau.api.services.CourseService;

@RestController
@RequestMapping("api/v1/course")
public class CourseController {

    private final CourseMapper courseMapper;
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseMapper courseMapper, CourseService courseService) {
        this.courseMapper = courseMapper;
        this.courseService = courseService;
    }

    @GetMapping(path = "{courseId}")
    public CourseGetDto getCourseById(
            @PathVariable("courseId") Long courseId) {
        Course course = courseService.getCourseById(courseId);
        return courseMapper.courseToCourseGetDto(course);
    }
    
    @PostMapping
    public void createCourse(@RequestBody CoursePostDto coursePostDto) {
        courseService.createCourse(coursePostDto);
    }
}
