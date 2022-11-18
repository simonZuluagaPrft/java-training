package srau.api.course;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import srau.api.grade.Grade;
import srau.api.lecture.Lecture;
import srau.api.student.Student;
import srau.api.subject.Subject;
import srau.api.teacher.Teacher;

@Data
@Entity
@Table
public class Course {
    
    @Id
    @SequenceGenerator(
        name = "course_sequence",
        sequenceName = "course_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "course_sequence"
    )
    private Long id;
    private String name;

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

    public Course(String name, Subject subject, Teacher teacher) {
        this.name = name;
        this.subject = subject;
        this.teacher = teacher;
    }

}
