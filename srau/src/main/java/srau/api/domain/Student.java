package srau.api.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = "courses")
@ToString(exclude = "courses")
@Entity
@Table
public class Student {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull(message = "Student should have a name")
    private String name;
    @Email(message = "Invalid email address")
    private String email;
    @OneToMany(mappedBy = "student")
    private Set<Grade> grades;
    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;

    public Student() {
    }

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
