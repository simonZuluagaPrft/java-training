package srau.api.mapstruct.mapper;

import org.mapstruct.Mapper;

import srau.api.domain.Student;
import srau.api.mapstruct.dto.StudentGetDto;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    
    StudentGetDto studentToStudentGetDto(Student student);
}
