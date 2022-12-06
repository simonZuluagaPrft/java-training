package srau.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import srau.api.domain.Course;
import srau.api.mapstruct.dto.CourseGetDto;
import srau.api.mapstruct.dto.CoursePostDto;
import srau.api.mapstruct.dto.StudentGetDto;
import srau.api.mapstruct.dto.StudentIdDto;
import srau.api.mapstruct.mapper.CourseMapper;
import srau.api.services.CourseService;

import java.util.List;

@RestController
@RequestMapping("api/v1/course")
public class CourseController {

    private final CourseMapper courseMapper;
    private final CourseService courseService;

    @Autowired
    public CourseController(
            CourseMapper courseMapper,
            CourseService courseService) {
        this.courseMapper = courseMapper;
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseGetDto> getCourses() {
        return courseService.getCourses();
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

    @PutMapping(path = "{courseId}")
    public void changeCourseTeacher(
            @PathVariable("courseId") Long courseId,
            @RequestParam(required = true) String teacherEmail) {
        courseService.changeCourseTeacher(courseId, teacherEmail);
    }

    @DeleteMapping(path = "{courseId}")
    public void deleteCourse(@PathVariable("courseId") Long courseId) {
        courseService.deleteCourse(courseId);
    }

    @GetMapping(path = "{courseId}/student")
    public List<StudentGetDto> getCourseStudents(
            @PathVariable("courseId") Long courseId) {
        return courseService.getCourseStudents(courseId);
    }

    @GetMapping(path = "{courseId}/passed")
    public List<StudentGetDto> getCoursePassedStudents(
            @PathVariable("courseId") Long courseId) {
        return courseService.getCoursePassedStudents(courseId);
    }

    @GetMapping(path = "{courseId}/failed")
    public List<StudentGetDto> getCourseFailedStudents(
            @PathVariable("courseId") Long courseId) {
        return courseService.getCourseFailedStudents(courseId);
    }

    @PostMapping(path = "{courseId}/student")
    public void enrollStudent(
            @PathVariable("courseId") Long courseId,
            @RequestBody StudentIdDto studentIdDto) {
        courseService.enrollStudent(courseId, studentIdDto.getStudentId());
    }

    @DeleteMapping(path = "{courseId}/student/{studentId}")
    public void dropStudent(
            @PathVariable("courseId") Long courseId,
            @PathVariable("studentId") Long studentId) {
        courseService.dropStudent(courseId, studentId);
    }

}
