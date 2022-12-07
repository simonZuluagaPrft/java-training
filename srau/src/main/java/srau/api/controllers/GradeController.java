package srau.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<GradeGetDto> getGrades() {
        return gradeService.getGrades();
    }

    @PostMapping
    public void createGrade(@RequestBody GradePostDto gradePostDto) {
        gradeService.createGrade(gradePostDto);
    }

    @DeleteMapping(path = "{gradeId}")
    public void deleteGrade(@PathVariable("gradeId") Long gradeId) {
        gradeService.deleteGrade(gradeId);
    }
}
