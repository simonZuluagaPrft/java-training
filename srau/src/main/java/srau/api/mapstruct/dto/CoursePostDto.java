package srau.api.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CoursePostDto {
    @NotNull(message = "subjectId is needed to create course")
    private Long subjectId;
    @NotNull(message = "teacherId is needed to create course")
    private Long teacherId;
}
