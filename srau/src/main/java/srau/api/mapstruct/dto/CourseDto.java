package srau.api.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;
import srau.api.domain.Subject;
import srau.api.domain.Teacher;

@Getter
@Setter
public class CourseDto {
    Subject subject;
    Teacher teacher;
}
