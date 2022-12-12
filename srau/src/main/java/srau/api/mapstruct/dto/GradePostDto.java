package srau.api.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradePostDto {
    private Integer score;
    private Long studentId;
    private Long courseId;
}
