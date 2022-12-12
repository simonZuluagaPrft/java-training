package srau.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srau.api.exception.ElementNotFoundException;
import srau.api.exception.ElementTakenException;
import srau.api.mapstruct.dto.*;
import srau.api.services.StudentService;

import java.util.List;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentGetDto>> getStudents() {
        return new ResponseEntity<>(studentService.getStudents(), HttpStatus.OK);
    }

    @GetMapping(path = "{studentEmail}")
    public ResponseEntity<StudentGetDto> getStudentByEmail(
            @PathVariable("studentEmail") String studentEmail) throws ElementNotFoundException {
        return new ResponseEntity<>(studentService.getStudentByEmail(studentEmail), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createStudent(@RequestBody StudentPostDto studentPostDto) throws ElementTakenException {
        studentService.createStudent(studentPostDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path = "{studentId}")
    public ResponseEntity<StudentGetDto> updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email)
            throws ElementNotFoundException, ElementTakenException {
        StudentGetDto studentGetDto = studentService.updateStudent(studentId, name, email);
        return new ResponseEntity<>(studentGetDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "{studentId}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("studentId") Long studentId)
            throws ElementNotFoundException {
        studentService.deleteStudent(studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "{studentId}/course")
    public ResponseEntity<List<CourseGetDto>> getStudentCourses(
            @PathVariable("studentId") Long studentId) throws ElementNotFoundException {
        return new ResponseEntity<>(studentService.getStudentCourses(studentId), HttpStatus.OK);
    }

    @GetMapping(path = "{studentId}/course/{courseId}")
    public ResponseEntity<Integer> getStudentGrade(
            @PathVariable("studentId") Long studentId,
            @PathVariable("courseId") Long courseId) throws ElementNotFoundException {
        Integer studentGrade = studentService.getStudentGrade(studentId, courseId);
        return new ResponseEntity<>(studentGrade, HttpStatus.OK);
    }

    @GetMapping(path = "{studentId}/subject")
    public ResponseEntity<List<SubjectGetDto>> getStudentSubjects(
            @PathVariable("studentId") Long studentId) throws ElementNotFoundException {
        return new ResponseEntity<>(studentService.getStudentSubjects(studentId), HttpStatus.OK);
    }

    @GetMapping(path = "{studentId}/schedule")
    public ResponseEntity<List<Schedule>> getStudentSchedule(
            @PathVariable("studentId") Long studentId) throws ElementNotFoundException {
        return new ResponseEntity<>(studentService.getStudentSchedule(studentId), HttpStatus.OK);
    }

    @GetMapping(path = "{studentId}/reportCard")
    public ResponseEntity<List<Report>> getStudentReportCard(
            @PathVariable("studentId") Long studentId) throws ElementNotFoundException {
        return new ResponseEntity<>(studentService.getStudentReportCard(studentId), HttpStatus.OK);
    }
}
