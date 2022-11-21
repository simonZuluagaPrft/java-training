package srau.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import srau.api.domain.Student;
import srau.api.mapstruct.dto.StudentGetDto;
import srau.api.mapstruct.dto.StudentPostDto;
import srau.api.mapstruct.mapper.MapStructMapper;
import srau.api.services.StudentService;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {

    private MapStructMapper mapStructMapper;
    private final StudentService studentService;

    @Autowired
    public StudentController(MapStructMapper mapStructMapper, StudentService studentService) {
        this.mapStructMapper = mapStructMapper;
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping(path = "{studentEmail}")
    public StudentGetDto getStudentByEmail(
            @PathVariable("studentEmail") String studentEmail) {
        Student student = studentService.getStudentByEmail(studentEmail);
        return mapStructMapper.studentToStudentGetDto(student);
    }

    @PostMapping
    public void createStudent(@RequestBody StudentPostDto studentPostDto) {
        Student student = mapStructMapper.studentPostDtoToStudent(studentPostDto);
        studentService.createStudent(student);
    }

}
