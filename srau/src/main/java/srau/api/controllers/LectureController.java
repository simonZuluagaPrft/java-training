package srau.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import srau.api.mapstruct.dto.LectureGetDto;
import srau.api.mapstruct.dto.LecturePostDto;
import srau.api.services.LectureService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/lecture")
public class LectureController {
    private final LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<List<LectureGetDto>> getLectures() {
        return new ResponseEntity<>(lectureService.getLectures(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<HttpStatus> createLecture(
            @RequestBody @Valid  LecturePostDto lecturePostDto) {
        lectureService.createLecture(lecturePostDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(path = "{lectureId}")
    public ResponseEntity<LectureGetDto> updateLecture(
            @PathVariable("lectureId") Long lectureId,
            @RequestParam(required = false) Integer dayOfWeek,
            @RequestParam(required = false) Integer startHour,
            @RequestParam(required = false) Integer finishHour) {
        LectureGetDto lectureGetDto = lectureService
                .updateLecture(lectureId, dayOfWeek, startHour, finishHour);
        return new ResponseEntity<>(lectureGetDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(path = "{lectureId}")
    public ResponseEntity<HttpStatus> deleteLecture(@PathVariable("lectureId") Long lectureId) {
        lectureService.deleteLecture(lectureId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
