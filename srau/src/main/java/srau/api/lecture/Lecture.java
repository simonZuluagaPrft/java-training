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

import srau.api.course.Course;

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

    public Lecture(Long id, DayOfWeek dayOfWeek, Integer startHour, Integer finishHour, Course course) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.startHour = startHour;
        this.finishHour = finishHour;
        this.course = course;
    }

    public Lecture(DayOfWeek dayOfWeek, Integer startHour, Integer finishHour, Course course) {
        this.dayOfWeek = dayOfWeek;
        this.startHour = startHour;
        this.finishHour = finishHour;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getFinishHour() {
        return finishHour;
    }

    public void setFinishHour(Integer finishHour) {
        this.finishHour = finishHour;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

}
