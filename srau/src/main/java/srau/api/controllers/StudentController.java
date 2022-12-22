package srau.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import srau.api.exception.ElementNotFoundException;
import srau.api.exception.ElementTakenException;
import srau.api.mapstruct.dto.*;
import srau.api.services.StudentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<List<StudentGetDto>> getStudents() {
        return new ResponseEntity<>(studentService.getStudents(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<HttpStatus> createStudent(
            @RequestBody @Valid StudentPostDto studentPostDto)
            throws ElementTakenException, ElementNotFoundException {
        studentService.createStudent(studentPostDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(path = "{studentId}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("studentId") Long studentId)
            throws ElementNotFoundException {
        studentService.deleteStudent(studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STUDENT')")
    @GetMapping(path = "{studentId}/course")
    public ResponseEntity<List<CourseGetDto>> getStudentCourses(
            @PathVariable("studentId") Long studentId) throws ElementNotFoundException {
        return new ResponseEntity<>(studentService.getStudentCourses(studentId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STUDENT')")
    @GetMapping(path = "{studentId}/course/{courseId}")
    public ResponseEntity<Integer> getStudentGrade(
            @PathVariable("studentId") Long studentId,
            @PathVariable("courseId") Long courseId) throws ElementNotFoundException {
        Integer studentGrade = studentService.getStudentGrade(studentId, courseId);
        return new ResponseEntity<>(studentGrade, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STUDENT')")
    @GetMapping(path = "{studentId}/subject")
    public ResponseEntity<List<SubjectGetDto>> getStudentSubjects(
            @PathVariable("studentId") Long studentId) throws ElementNotFoundException {
        return new ResponseEntity<>(studentService.getStudentSubjects(studentId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STUDENT')")
    @GetMapping(path = "{studentId}/schedule")
    public ResponseEntity<List<Schedule>> getStudentSchedule(
            @PathVariable("studentId") Long studentId) throws ElementNotFoundException {
        return new ResponseEntity<>(studentService.getStudentSchedule(studentId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STUDENT')")
    @GetMapping(path = "{studentId}/reportCard")
    public ResponseEntity<List<Report>> getStudentReportCard(
            @PathVariable("studentId") Long studentId) throws ElementNotFoundException {
        return new ResponseEntity<>(studentService.getStudentReportCard(studentId), HttpStatus.OK);
    }
}
