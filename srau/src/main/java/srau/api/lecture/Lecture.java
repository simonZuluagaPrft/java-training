package srau.api.lecture;

import java.time.DayOfWeek;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import srau.api.course.Course;

@Data
@Entity
@Table
public class Lecture {
    
    @Id
    @SequenceGenerator(
        name = "lecture_sequence",
        sequenceName = "lecture_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "lecture_sequence"
    )
    private Long id;
    @Enumerated(value = EnumType.STRING)
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
