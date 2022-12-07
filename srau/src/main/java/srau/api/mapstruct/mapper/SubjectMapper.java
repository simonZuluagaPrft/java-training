package srau.api.mapstruct.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import srau.api.domain.Subject;
import srau.api.mapstruct.dto.SubjectGetDto;
import srau.api.mapstruct.dto.SubjectPostDto;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    SubjectGetDto subjectToSubjectGetDto(Subject subject);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    Subject subjectPostDtoToSubject(SubjectPostDto subjectPostDto);
}
