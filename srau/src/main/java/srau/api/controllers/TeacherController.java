package srau.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srau.api.domain.Teacher;
import srau.api.exception.BussinesLogicException;
import srau.api.exception.ElementNotFoundException;
import srau.api.exception.ElementTakenException;
import srau.api.mapstruct.dto.GradeGetDto;
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

    public TeacherController(TeacherService teacherService, TeacherMapper teacherMapper) {
        this.teacherService = teacherService;
        this.teacherMapper = teacherMapper;
    }

    @GetMapping
    public ResponseEntity<List<TeacherGetDto>> getTeachers() {
        return new ResponseEntity<>(teacherService.getTeachers(), HttpStatus.OK);
    }

    @GetMapping(path = "{teacherEmail}")
    public ResponseEntity<TeacherGetDto> getTeacherByEmail(
            @PathVariable("teacherEmail") String teacherEmail) throws ElementNotFoundException {
        Teacher teacher = teacherService.getTeacherByEmail(teacherEmail);
        return new ResponseEntity<>(teacherMapper.teacherToTeacherGetDto(teacher), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createTeacher(@RequestBody TeacherPostDto teacherPostDto)
            throws ElementTakenException {
        Teacher teacher = teacherMapper.teacherPostDtoToTeacher(teacherPostDto);
        teacherService.createTeacher(teacher);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path = "{teacherId}")
    public ResponseEntity<TeacherGetDto> updateTeacher(
            @PathVariable("teacherId") Long teacherId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email)
            throws ElementNotFoundException, ElementTakenException {
        return new ResponseEntity<>(teacherService.updateTeacher(teacherId, name, email), HttpStatus.OK);
    }

    @DeleteMapping(path = "{teacherId}")
    public ResponseEntity deleteTeacher(@PathVariable("teacherId") Long teacherId)
            throws ElementNotFoundException {
        teacherService.deleteTeacher(teacherId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(path = "{teacherId}/grade")
    public ResponseEntity<HttpStatus> gradeStudent(
            @PathVariable("teacherId") Long teacherId,
            @RequestBody GradePostDto gradePostDto)
            throws BussinesLogicException, ElementNotFoundException {
        teacherService.gradeStudent(teacherId, gradePostDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path = "{teacherId}/grade")
    public ResponseEntity<GradeGetDto> updateStudentGrade(
            @PathVariable("teacherId") Long teacherId,
            @RequestBody GradePostDto gradePostDto)
            throws BussinesLogicException, ElementNotFoundException {
        GradeGetDto gradeGetDto = teacherService.updateStudentGrade(teacherId, gradePostDto);

        return new ResponseEntity<>(gradeGetDto, HttpStatus.OK);
    }
}
