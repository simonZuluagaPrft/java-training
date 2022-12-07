package srau.api.mapstruct.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import srau.api.domain.Grade;
import srau.api.mapstruct.dto.GradeGetDto;

@Mapper(componentModel = "spring")
public interface GradeMapper {
    @Mapping(target = "studentId", source = "grade.student.id")
    @Mapping(target = "courseId", source = "grade.course.id")
    GradeGetDto gradeToGradeGetDto(Grade grade);
}
