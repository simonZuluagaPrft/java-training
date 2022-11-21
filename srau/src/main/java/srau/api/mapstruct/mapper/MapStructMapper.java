package srau.api.mapstruct.mapper;

import org.mapstruct.Mapper;

import srau.api.domain.Student;
import srau.api.mapstruct.dto.StudentGetDto;
import srau.api.mapstruct.dto.StudentPostDto;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    
    StudentGetDto studentToStudentGetDto(Student student);

    // @Mapping(target = "courses");
    Student studentPostDtoToStudent(StudentPostDto studentPostDto);
}
