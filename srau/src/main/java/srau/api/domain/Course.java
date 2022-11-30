package srau.api.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.*;

@Data
@EqualsAndHashCode(exclude = "students")
@ToString(exclude = "students")
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

    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(
            name = "course_id",
            referencedColumnName = "id"
            ),
        inverseJoinColumns = @JoinColumn(
            name = "student_id",
            referencedColumnName = "id"
            )
    )
    private Set<Student> students;

    public Course() {
    }

    public Course(Subject subject, Teacher teacher) {
        this.subject = subject;
        this.teacher = teacher;
    }

    public void addStudent(Student student) {
        this.students.add(student);
        student.getCourses().add(this);
    }

    public void deleteStudent(Student student) {
        this.students.remove(student);
        student.getCourses().remove(this);
    }

}
