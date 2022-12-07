package srau.api.mapstruct.mapper;

import org.mapstruct.Mapper;

import srau.api.domain.Course;
import srau.api.mapstruct.dto.CourseGetDto;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseGetDto courseToCourseGetDto(Course course);
}
