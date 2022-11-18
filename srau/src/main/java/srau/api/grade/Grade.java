package srau.api.grade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import srau.api.course.Course;
import srau.api.student.Student;

@Data
@Entity
@Table
public class Grade {
    
    @Id
    @SequenceGenerator(
        name = "grade_sequence",
        sequenceName = "grade_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "grade_sequence"
    )
    private Long id;
    private int score;
    
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
