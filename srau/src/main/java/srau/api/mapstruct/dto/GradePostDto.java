package srau.api.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GradePostDto {
    @NotNull(message = "score is needed to create a grade")
    private Integer score;
    @NotNull(message = "studentId is needed to create grade")
    private Long studentId;
    @NotNull(message = "courseId is needed to create grade")
    private Long courseId;
}
