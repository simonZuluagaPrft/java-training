package srau.api.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseGetDto {
    private Long id;
    private SubjectGetDto subject;
    private TeacherGetDto teacher;
}
