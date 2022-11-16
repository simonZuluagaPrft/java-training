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

import srau.api.grade.Grade;
import srau.api.lecture.Lecture;
import srau.api.student.Student;
import srau.api.subject.Subject;
import srau.api.teacher.Teacher;

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

    public Course(Long id, String name, Subject subject, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.teacher = teacher;
    }

    public Course(String name, Subject subject, Teacher teacher) {
        this.name = name;
        this.subject = subject;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    
    public Set<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(Set<Lecture> lectures) {
        this.lectures = lectures;
    }
    
    public Set<Grade> getGrades() {
        return grades;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

}
