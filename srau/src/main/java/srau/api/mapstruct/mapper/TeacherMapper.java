package srau.api.mapstruct.mapper;

import org.mapstruct.Mapper;
import srau.api.domain.Teacher;
import srau.api.mapstruct.dto.TeacherGetDto;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherGetDto teacherToTeacherGetDto(Teacher teacher);
}
