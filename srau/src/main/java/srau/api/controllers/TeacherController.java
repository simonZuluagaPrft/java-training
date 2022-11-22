package srau.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import srau.api.domain.Teacher;
import srau.api.mapstruct.dto.TeacherGetDto;
import srau.api.mapstruct.dto.TeacherPostDto;
import srau.api.mapstruct.mapper.TeacherMapper;
import srau.api.services.TeacherService;

@RestController
@RequestMapping("api/v1/teacher")
public class TeacherController {
    
    private final TeacherMapper teacherMapper;
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherMapper teacherMapper, TeacherService teacherService) {
        this.teacherMapper = teacherMapper;
        this.teacherService = teacherService;
    }


    @GetMapping
    public List<Teacher> getTeachers() {
        return teacherService.getTeachers();
    }

    @GetMapping(path = "{teacherEmail}")
    public TeacherGetDto getTeacherByEmail(
            @PathVariable("teacherEmail") String teacherEmail) {
        Teacher teacher = teacherService.getTeacherByEmail(teacherEmail);
        return teacherMapper.teacherToStudentGetDto(teacher);
    }

    @PostMapping
    public void createStudent(@RequestBody TeacherPostDto teacherPostDto) {
        Teacher teacher = teacherMapper.teacherPostDtoToTeacher(teacherPostDto);
        teacherService.createTeacher(teacher);
    }
}
