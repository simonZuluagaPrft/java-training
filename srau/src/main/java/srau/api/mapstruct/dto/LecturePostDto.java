package srau.api.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LecturePostDto {
    @NotNull(message = "courseId is needed to create lecture")
    private Long courseId;
    @NotNull(message = "dayOfWeek is needed to create lecture")
    private Integer dayOfWeek;
    @NotNull(message = "startHour is needed to create lecture")
    private Integer startHour;
    @NotNull(message = "finishHour is needed to create lecture")
    private Integer finishHour;
}
