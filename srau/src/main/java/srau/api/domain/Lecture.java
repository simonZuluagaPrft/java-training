package srau.api.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;

@Data
@Entity
@Table
public class Lecture {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull(message = "Lecture needs a day of the week")
    private DayOfWeek dayOfWeek;
    @NotNull(message = "Lecture needs a start hour")
    @Min(value = 7)
    @Max(value = 19)
    private Integer startHour;
    @NotNull(message = "Lecture needs a finish hour")
    @Min(value = 9)
    @Max(value = 21)
    private Integer finishHour;
    @NotNull(message = "Lecture needs a course")
    @ManyToOne
    private Course course;

    public Lecture() {
    }

    public Lecture(DayOfWeek dayOfWeek, Integer startHour, Integer finishHour, Course course) {
        this.dayOfWeek = dayOfWeek;
        this.startHour = startHour;
        this.finishHour = finishHour;
        this.course = course;
    }
}
