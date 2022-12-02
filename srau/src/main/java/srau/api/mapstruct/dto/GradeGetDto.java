package srau.api.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeGetDto {

    private Long id;
    private Integer score;
    private Long studentId;
    private Long courseId;

}
