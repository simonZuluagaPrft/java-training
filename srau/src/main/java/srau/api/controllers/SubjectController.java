package srau.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;

    @Autowired
    public SubjectController(SubjectService subjectService, SubjectMapper subjectMapper) {
        this.subjectService = subjectService;
        this.subjectMapper = subjectMapper;
    }

    @GetMapping
    public ResponseEntity<List<SubjectGetDto>> getSubjects() {
        return new ResponseEntity<>(subjectService.getSubjects(), HttpStatus.OK);
    }

    @GetMapping(path = "{subjectName}")
    public ResponseEntity<SubjectGetDto> getSubjectByName(
            @PathVariable("subjectName") String subjectName) {
        Subject subject = subjectService.getSubjectByName(subjectName);
        return new ResponseEntity<>(subjectMapper.subjectToSubjectGetDto(subject), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createSubject(@RequestBody SubjectPostDto subjectPostDto) {
        Subject subject = subjectMapper.subjectPostDtoToSubject(subjectPostDto);
        subjectService.createSubject(subject);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path = "{subjectId}")
    public ResponseEntity<SubjectGetDto> updateSubject(
            @PathVariable("subjectId") Long subjectId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        SubjectGetDto subjectGetDto = subjectService.updateSubject(subjectId, name, email);

        return new ResponseEntity<>(subjectGetDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "{subjectId}")
    public ResponseEntity<HttpStatus> deleteSubject(@PathVariable("subjectId") Long subjectId) {
        subjectService.deleteSubject(subjectId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
