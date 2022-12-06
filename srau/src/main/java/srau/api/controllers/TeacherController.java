package srau.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import srau.api.domain.Teacher;
import srau.api.mapstruct.dto.GradePostDto;
import srau.api.mapstruct.dto.TeacherGetDto;
import srau.api.mapstruct.dto.TeacherPostDto;
import srau.api.mapstruct.mapper.TeacherMapper;
import srau.api.services.TeacherService;

import java.util.List;

@RestController
@RequestMapping("api/v1/teacher")
public class TeacherController {

    private final TeacherService teacherService;
    private final TeacherMapper teacherMapper;

    @Autowired
    public TeacherController(TeacherMapper teacherMapper, TeacherService teacherService) {
        this.teacherMapper = teacherMapper;
        this.teacherService = teacherService;
    }


    @GetMapping
    public List<TeacherGetDto> getTeachers() {
        return teacherService.getTeachers();
    }

    @GetMapping(path = "{teacherEmail}")
    public TeacherGetDto getTeacherByEmail(
            @PathVariable("teacherEmail") String teacherEmail) {
        Teacher teacher = teacherService.getTeacherByEmail(teacherEmail);
        return teacherMapper.teacherToTeacherGetDto(teacher);
    }

    @PostMapping
    public void createTeacher(@RequestBody TeacherPostDto teacherPostDto) {
        Teacher teacher = teacherMapper.teacherPostDtoToTeacher(teacherPostDto);
        teacherService.createTeacher(teacher);
    }

    @PutMapping(path = "{teacherId}")
    public void updateTeacher(
            @PathVariable("teacherId") Long teacherId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        teacherService.updateTeacher(teacherId, name, email);
    }

    @DeleteMapping(path = "{teacherId}")
    public void deleteTeacher(@PathVariable("teacherId") Long teacherId) {
        teacherService.deleteTeacher(teacherId);
    }

    @PostMapping(path = "{teacherId}/grade")
    public void gradeStudent(
            @PathVariable("teacherId") Long teacherId,
            @RequestBody GradePostDto gradePostDto) {
        teacherService.gradeStudent(teacherId, gradePostDto);
    }

    @PutMapping(path = "{teacherId}/grade")
    public void updateStudentGrade(
            @PathVariable("teacherId") Long teacherId,
            @RequestBody GradePostDto gradePostDto) {
        teacherService.updateGrade(teacherId, gradePostDto);
    }
}
