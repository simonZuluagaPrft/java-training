package srau.api.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"student_id", "course_id"})})
@EqualsAndHashCode(exclude = {"student", "course"})
@ToString(exclude = {"student", "course"})
public class Grade {
    @Id
    @GeneratedValue
    private Long id;
    private Integer score;
    @NotNull(message = "Grade needs a student")
    @ManyToOne
    private Student student;
    @NotNull(message = "Course needs a course")
    @ManyToOne
    private Course course;

    public Grade() {
    }

    public Grade(int score, Student student, Course course) {
        this.score = score;
        this.student = student;
        this.course = course;
    }
}
