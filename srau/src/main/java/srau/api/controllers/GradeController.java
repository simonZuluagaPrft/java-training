package srau.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import srau.api.exception.ElementNotFoundException;
import srau.api.mapstruct.dto.GradeGetDto;
import srau.api.mapstruct.dto.GradePostDto;
import srau.api.services.GradeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/grade")
public class GradeController {
    private final GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PreAuthorize("hasAuthority('ADMIN', 'STUDENT', 'TEACHER')")
    @GetMapping(path = "student/{studentId}")
    public ResponseEntity<List<GradeGetDto>> getStudentGrades(
            @PathVariable("studentId") Long studentId) throws ElementNotFoundException {
        return new ResponseEntity<>(gradeService.getStudentGrades(studentId), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN', 'TEACHER')")
    @GetMapping(path = "course/{courseId}")
    public ResponseEntity<List<GradeGetDto>> getCourseGrades(
            @PathVariable("courseId") Long courseId) throws ElementNotFoundException {
        return new ResponseEntity<>(gradeService.getCourseGrades(courseId), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN', 'TEACHER')")
    @PostMapping
    public ResponseEntity<HttpStatus> createGrade(@RequestBody @Valid GradePostDto gradePostDto) {
        gradeService.createGrade(gradePostDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN', 'TEACHER')")
    @DeleteMapping(path = "{gradeId}")
    public ResponseEntity<HttpStatus> deleteGrade(@PathVariable("gradeId") Long gradeId) {
        gradeService.deleteGrade(gradeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
