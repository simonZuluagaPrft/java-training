package srau.api.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturePostDto {
    private Long courseId;
    private Integer dayOfWeek;
    private Integer startHour;
    private Integer finishHour;
}
