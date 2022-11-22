package srau.api.mapstruct.mapper;

import org.mapstruct.Mapper;

import srau.api.domain.Subject;
import srau.api.mapstruct.dto.SubjectGetDto;
import srau.api.mapstruct.dto.SubjectPostDto;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    
    SubjectGetDto subjectToSubjectGetDto(Subject subject);

    // @Mapping(target = "courses");
    Subject subjectPostDtoToSubject(SubjectPostDto subjectPostDto);
}
