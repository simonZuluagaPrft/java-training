package srau.api.mapstruct.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import srau.api.domain.Lecture;
import srau.api.mapstruct.dto.LectureGetDto;

@Mapper(componentModel = "spring")
public interface LectureMapper {
    @Mapping(target = "courseId", source = "lecture.course.id")
    @Mapping(target = "dayOfWeek", expression = "java(lecture.getDayOfWeek().getValue())")
    LectureGetDto lectureToLectureGetDto(Lecture lecture);
}
