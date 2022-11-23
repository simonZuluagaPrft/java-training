package srau.api.mapstruct.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import srau.api.domain.Teacher;
import srau.api.mapstruct.dto.TeacherGetDto;
import srau.api.mapstruct.dto.TeacherPostDto;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    
    TeacherGetDto teacherToTeacherGetDto(Teacher teacher);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    Teacher teacherPostDtoToTeacher(TeacherPostDto teacherPostDto);
}
