package srau.api.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = "courses")
@ToString(exclude = "courses")
@Entity
@Table
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "Student should have a name")
    private String name;
    @NotBlank(message = "Student should have an email")
    @Email(message = "Invalid email address")
    private String email;
    @OneToMany(mappedBy = "student")
    private Set<Grade> grades;
    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
