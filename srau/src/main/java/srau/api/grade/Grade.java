package srau.api.grade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import srau.api.course.Course;
import srau.api.student.Student;

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

    public Grade(Long id, int score, Student student, Course course) {
        this.id = id;
        this.score = score;
        this.student = student;
        this.course = course;
    }

    public Grade(int score, Student student, Course course) {
        this.score = score;
        this.student = student;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

}
