package srau.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srau.api.mapstruct.dto.GradeGetDto;
import srau.api.mapstruct.dto.GradePostDto;
import srau.api.services.GradeService;

import java.util.List;

@RestController
@RequestMapping("api/v1/grade")
public class GradeController {
    private final GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    public ResponseEntity<List<GradeGetDto>> getGrades() {
        return new ResponseEntity<>(gradeService.getGrades(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createGrade(@RequestBody GradePostDto gradePostDto) {
        gradeService.createGrade(gradePostDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{gradeId}")
    public ResponseEntity<HttpStatus> deleteGrade(@PathVariable("gradeId") Long gradeId) {
        gradeService.deleteGrade(gradeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
