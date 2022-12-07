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

import srau.api.mapstruct.dto.LectureGetDto;
import srau.api.mapstruct.dto.LecturePostDto;
import srau.api.services.LectureService;

@RestController
@RequestMapping("api/v1/lecture")
public class LectureController {
    private final LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping
    public List<LectureGetDto> getLectures() {
        return lectureService.getLectures();
    }

    @PostMapping
    public void createLecture(@RequestBody LecturePostDto lecturePostDto) {
        lectureService.createLecture(lecturePostDto);
    }

    @PutMapping(path = "{lectureId}")
    public void updateLecture(
            @PathVariable("lectureId") Long lectureId,
            @RequestParam(required = false) Integer dayOfWeek,
            @RequestParam(required = false) Integer startHour,
            @RequestParam(required = false) Integer finishHour) {
        lectureService.updateLecture(lectureId, dayOfWeek, startHour, finishHour);
    }

    @DeleteMapping(path = "{lectureId}")
    public void deleteLecture(@PathVariable("lectureId") Long lectureId) {
        lectureService.deleteLecture(lectureId);
    }
}
