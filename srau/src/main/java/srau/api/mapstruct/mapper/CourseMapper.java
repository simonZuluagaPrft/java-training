package srau.api.mapstruct.mapper;

import org.mapstruct.Mapper;

import srau.api.domain.Course;
import srau.api.mapstruct.dto.CourseGetDto;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    
    // @Mapping(target = "subjectId", source = "subject.id")
    // @Mapping(target = "teacherId", source = "teacher.id")
    CourseGetDto courseToCourseGetDto(Course course);
}
