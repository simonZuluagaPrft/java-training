package srau.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import srau.api.exception.BussinesLogicException;
import srau.api.exception.ElementNotFoundException;
import srau.api.exception.ElementTakenException;
import srau.api.mapstruct.dto.GradeGetDto;
import srau.api.mapstruct.dto.GradePostDto;
import srau.api.mapstruct.dto.TeacherGetDto;
import srau.api.mapstruct.dto.TeacherPostDto;
import srau.api.services.TeacherService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<List<TeacherGetDto>> getTeachers() {
        return new ResponseEntity<>(teacherService.getTeachers(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<HttpStatus> createTeacher(
            @RequestBody @Valid TeacherPostDto teacherPostDto)
            throws ElementTakenException, ElementNotFoundException {
        teacherService.createTeacher(teacherPostDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(path = "{teacherId}")
    public ResponseEntity<HttpStatus> deleteTeacher(@PathVariable("teacherId") Long teacherId)
            throws ElementNotFoundException {
        teacherService.deleteTeacher(teacherId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TEACHER')")
    @PostMapping(path = "{teacherId}/grade")
    public ResponseEntity<HttpStatus> gradeStudent(
            @PathVariable("teacherId") Long teacherId,
            @RequestBody GradePostDto gradePostDto)
            throws BussinesLogicException, ElementNotFoundException {
        teacherService.gradeStudent(teacherId, gradePostDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TEACHER')")
    @PutMapping(path = "{teacherId}/grade")
    public ResponseEntity<GradeGetDto> updateStudentGrade(
            @PathVariable("teacherId") Long teacherId,
            @RequestBody GradePostDto gradePostDto)
            throws BussinesLogicException, ElementNotFoundException {
        GradeGetDto gradeGetDto = teacherService.updateStudentGrade(teacherId, gradePostDto);

        return new ResponseEntity<>(gradeGetDto, HttpStatus.OK);
    }
}
