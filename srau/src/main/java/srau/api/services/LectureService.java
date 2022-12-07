package srau.api.services;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import srau.api.domain.Course;
import srau.api.domain.Lecture;
import srau.api.mapstruct.dto.LectureGetDto;
import srau.api.mapstruct.dto.LecturePostDto;
import srau.api.mapstruct.mapper.LectureMapper;
import srau.api.repositories.CourseRepository;
import srau.api.repositories.LectureRepository;

@Service
public class LectureService {
    private final CourseRepository courseRepository;
    private final LectureRepository lectureRepository;
    private final LectureMapper lectureMapper;

    @Autowired
    public LectureService(
            CourseRepository courseRepository,
            LectureRepository lectureRepository,
            LectureMapper lectureMapper) {
        this.courseRepository = courseRepository;
        this.lectureRepository = lectureRepository;
        this.lectureMapper = lectureMapper;
    }

    public List<LectureGetDto> getLectures() {
        List<Lecture> lectures = lectureRepository.findAll();

        return lectures
                .stream()
                .map(lectureMapper::lectureToLectureGetDto)
                .collect(Collectors.toList());
    }

    public void createLecture(LecturePostDto lecturePostDto) {
        Course course = courseRepository.findById(lecturePostDto.getCourseId())
                .orElseThrow(() -> new IllegalStateException(
                        "Course with id " + lecturePostDto.getCourseId() + "does not exists"));
        Lecture lecture = new Lecture(
                DayOfWeek.of(lecturePostDto.getDayOfWeek()),
                lecturePostDto.getStartHour(),
                lecturePostDto.getFinishHour(),
                course);
        lectureRepository.save(lecture);
    }

    @Transactional
    public LectureGetDto updateLecture(
            Long lectureId,
            Integer dayOfWeek,
            Integer startHour,
            Integer finishHour) {
        Lecture lecture = lectureRepository
                .findById(lectureId)
                .orElseThrow(() -> new IllegalStateException(
                        "Lecture with id " + lectureId + "does not exists"));

        if (dayOfWeek != null) {
            lecture.setDayOfWeek(DayOfWeek.of(dayOfWeek));
        }

        if (startHour != null) {
            lecture.setStartHour(startHour);
        }

        if (finishHour != null) {
            lecture.setFinishHour(finishHour);
        }

        return lectureMapper.lectureToLectureGetDto(lecture);
    }

    public void deleteLecture(Long lectureId) {
        boolean exists = lectureRepository.existsById(lectureId);

        if (!exists) {
            throw new IllegalStateException("Lecture with id " + lectureId + "does not exists");
        }

        lectureRepository.deleteById(lectureId);
    }
}
