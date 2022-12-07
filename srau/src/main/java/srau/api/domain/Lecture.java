package srau.api.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.DayOfWeek;

@Data
@Entity
@Table
public class Lecture {
    @Id
    @GeneratedValue
    private Long id;
    private DayOfWeek dayOfWeek;
    private Integer startHour;
    private Integer finishHour;
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
