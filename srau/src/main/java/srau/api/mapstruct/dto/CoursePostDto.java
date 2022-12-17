package srau.api.mapstruct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoursePostDto {
    @NotNull(message = "subjectId is needed to create course")
    private Long subjectId;
    @NotNull(message = "teacherId is needed to create course")
    private Long teacherId;
}
