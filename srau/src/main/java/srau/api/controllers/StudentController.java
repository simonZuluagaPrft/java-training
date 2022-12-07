package srau.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import srau.api.domain.Student;
import srau.api.mapstruct.dto.*;
import srau.api.mapstruct.mapper.StudentMapper;
import srau.api.services.StudentService;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentController(StudentService studentService, StudentMapper studentMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    @GetMapping
    public List<StudentGetDto> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping(path = "{studentEmail}")
    public StudentGetDto getStudentByEmail(@PathVariable("studentEmail") String studentEmail) {
        Student student = studentService.getStudentByEmail(studentEmail);
        return studentMapper.studentToStudentGetDto(student);
    }

    @PostMapping
    public void createStudent(@RequestBody StudentPostDto studentPostDto) {
        Student student = studentMapper.studentPostDtoToStudent(studentPostDto);
        studentService.createStudent(student);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        studentService.updateStudent(studentId, name, email);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }

    @GetMapping(path = "{studentId}/course")
    public List<CourseGetDto> getStudentCourses(@PathVariable("studentId") Long studentId) {
        return studentService.getStudentCourses(studentId);
    }

    @GetMapping(path = "{studentId}/course/{courseId}")
    public Integer getStudentGrade(
            @PathVariable("studentId") Long studentId,
            @PathVariable("courseId") Long courseId) {
        return studentService.getStudentGrade(studentId, courseId);
    }

    @GetMapping(path = "{studentId}/subject")
    public List<SubjectGetDto> getStudentSubjects(@PathVariable("studentId") Long studentId) {
        return studentService.getStudentSubjects(studentId);
    }

    @GetMapping(path = "{studentId}/schedule")
    public List<Schedule> getStudentSchedule(@PathVariable("studentId") Long studentId) {
        return studentService.getStudentSchedule(studentId);
    }

    @GetMapping(path = "{studentId}/reportCard")
    public List<Report> getStudentReportCard(@PathVariable("studentId") Long studentId) {
        return studentService.getStudentReportCard(studentId);
    }
}
