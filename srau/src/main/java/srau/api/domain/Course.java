package srau.api.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table
public class Course {
    
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Subject subject;
    @ManyToOne
    private Teacher teacher;

    @OneToMany(mappedBy = "course")
    private Set<Lecture> lectures;

    @OneToMany(mappedBy = "course")
    private Set<Grade> grades;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students;

    public Course() {
    }

    public Course(Subject subject, Teacher teacher) {
        this.subject = subject;
        this.teacher = teacher;
    }

}
