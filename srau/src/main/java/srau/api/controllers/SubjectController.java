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

import srau.api.domain.Subject;
import srau.api.mapstruct.dto.SubjectGetDto;
import srau.api.mapstruct.dto.SubjectPostDto;
import srau.api.mapstruct.mapper.SubjectMapper;
import srau.api.services.SubjectService;

@RestController
@RequestMapping("api/v1/subject")
public class SubjectController {
    
    private final SubjectMapper subjectMapper;
    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectMapper subjectMapper, SubjectService subjectService) {
        this.subjectMapper = subjectMapper;
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<SubjectGetDto> getSubjects() {
        return subjectService.getSubjects();
    }

    @GetMapping(path = "{subjectName}")
    public SubjectGetDto getSubjectByName(
            @PathVariable("subjectName") String subjectName) {
        Subject subject = subjectService.getSubjectByName(subjectName);
        return subjectMapper.subjectToSubjectGetDto(subject);
    }

    @PostMapping
    public void createSubject(@RequestBody SubjectPostDto subjectPostDto) {
        Subject subject = subjectMapper.subjectPostDtoToSubject(subjectPostDto);
        subjectService.createSubject(subject);
    }

    @PutMapping(path = "{subjectId}")
    public void updateSubject(
            @PathVariable("subjectId") Long subjectId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        subjectService.updateSubject(subjectId, name, email);
    }

    @DeleteMapping(path = "{subjectId}")
    public void deleteSubject(@PathVariable("subjectId") Long subjectId) {
        subjectService.deleteSubject(subjectId);
    }
}
