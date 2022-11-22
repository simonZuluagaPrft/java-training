package srau.api.mapstruct.mapper;

import org.mapstruct.Mapper;

import srau.api.domain.Teacher;
import srau.api.mapstruct.dto.TeacherGetDto;
import srau.api.mapstruct.dto.TeacherPostDto;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    
    TeacherGetDto teacherToTeacherGetDto(Teacher teacher);

    // @Mapping(target = "courses");
    Teacher teacherPostDtoToTeacher(TeacherPostDto teacherPostDto);
}
