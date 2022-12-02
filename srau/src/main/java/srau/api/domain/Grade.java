package srau.api.domain;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_id", "course_id"})
        }
)
public class Grade {

    @Id
    @GeneratedValue
    private Long id;
    private Integer score;

    @ManyToOne
    private Student student;
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
