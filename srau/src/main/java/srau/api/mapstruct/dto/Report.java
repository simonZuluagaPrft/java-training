package srau.api.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Report {
    private String subjectName;
    private String teacherName;
    private Integer score;

    public Report(String subjectName, String teacherName, Integer score) {
        this.subjectName = subjectName;
        this.teacherName = teacherName;
        this.score = score;
    }
}
