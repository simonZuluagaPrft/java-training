package srau.api.mapstruct.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import srau.api.domain.Student;
import srau.api.mapstruct.dto.StudentGetDto;
import srau.api.mapstruct.dto.StudentPostDto;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentGetDto studentToStudentGetDto(Student student);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "grades", ignore = true)
    Student studentPostDtoToStudent(StudentPostDto studentPostDto);
}
