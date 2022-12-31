package srau.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import srau.api.exception.ElementNotFoundException;
import srau.api.exception.ElementTakenException;
import srau.api.mapstruct.dto.SubjectGetDto;
import srau.api.mapstruct.dto.SubjectPostDto;
import srau.api.services.SubjectService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/subject")
public class SubjectController {
    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public ResponseEntity<List<SubjectGetDto>> getSubjects() {
        return new ResponseEntity<>(subjectService.getSubjects(), HttpStatus.OK);
    }

    @GetMapping(path = "{subjectName}")
    public ResponseEntity<SubjectGetDto> getSubjectByName(
            @PathVariable("subjectName") String subjectName) throws ElementNotFoundException {
        return new ResponseEntity<>(subjectService.getSubjectByName(subjectName), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<HttpStatus> createSubject(
            @RequestBody @Valid SubjectPostDto subjectPostDto)
            throws ElementTakenException {
        subjectService.createSubject(subjectPostDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(path = "{subjectId}")
    public ResponseEntity<SubjectGetDto> updateSubject(
            @PathVariable("subjectId") Long subjectId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email)
            throws ElementNotFoundException, ElementTakenException {
        SubjectGetDto subjectGetDto = subjectService.updateSubject(subjectId, name, email);
        return new ResponseEntity<>(subjectGetDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(path = "{subjectId}")
    public ResponseEntity<HttpStatus> deleteSubject(@PathVariable("subjectId") Long subjectId)
            throws ElementNotFoundException {
        subjectService.deleteSubject(subjectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
