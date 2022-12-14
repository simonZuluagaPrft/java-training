package srau.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srau.api.exception.BussinesLogicException;
import srau.api.exception.ElementNotFoundException;
import srau.api.mapstruct.dto.CourseGetDto;
import srau.api.mapstruct.dto.CoursePostDto;
import srau.api.mapstruct.dto.StudentGetDto;
import srau.api.mapstruct.dto.StudentIdDto;
import srau.api.services.CourseService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/course")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseGetDto>> getCourses() {
        return new ResponseEntity<>(courseService.getCourses(), HttpStatus.OK);
    }

    @GetMapping(path = "{courseId}")
    public ResponseEntity<CourseGetDto> getCourseById(@PathVariable("courseId") Long courseId)
            throws ElementNotFoundException {
        return new ResponseEntity<>(courseService.getCourseById(courseId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createCourse(@RequestBody @Valid CoursePostDto coursePostDto)
            throws ElementNotFoundException {
        courseService.createCourse(coursePostDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path = "{courseId}")
    public ResponseEntity<CourseGetDto> changeCourseTeacher(
            @PathVariable("courseId") Long courseId,
            @RequestParam(required = true) Long teacherId) throws ElementNotFoundException {
        CourseGetDto course = courseService.changeCourseTeacher(courseId, teacherId);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @DeleteMapping(path = "{courseId}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable("courseId") Long courseId)
            throws ElementNotFoundException {
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "{courseId}/student")
    public ResponseEntity<List<StudentGetDto>> getCourseStudents(
            @PathVariable("courseId") Long courseId) throws ElementNotFoundException {
        return new ResponseEntity<>(courseService.getCourseStudents(courseId), HttpStatus.OK);
    }

    @GetMapping(path = "{courseId}/passed")
    public ResponseEntity<List<StudentGetDto>> getCoursePassedStudents(
            @PathVariable("courseId") Long courseId) throws ElementNotFoundException {
        return new ResponseEntity<>(courseService.getCoursePassedStudents(courseId), HttpStatus.OK);
    }

    @GetMapping(path = "{courseId}/failed")
    public ResponseEntity<List<StudentGetDto>> getCourseFailedStudents(
            @PathVariable("courseId") Long courseId) throws ElementNotFoundException {
        return new ResponseEntity<>(courseService.getCourseFailedStudents(courseId), HttpStatus.OK);
    }

    @PostMapping(path = "{courseId}/student")
    public ResponseEntity<HttpStatus> enrollStudent(
            @PathVariable("courseId") Long courseId,
            @RequestBody StudentIdDto studentIdDto)
            throws ElementNotFoundException, BussinesLogicException {
        courseService.enrollStudent(courseId, studentIdDto.getStudentId());
        System.out.println(HttpStatus.CREATED.getClass());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{courseId}/student/{studentId}")
    public ResponseEntity<HttpStatus> dropStudent(
            @PathVariable("courseId") Long courseId,
            @PathVariable("studentId") Long studentId) throws ElementNotFoundException {
        courseService.dropStudent(courseId, studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
