package srau.api.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table
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
