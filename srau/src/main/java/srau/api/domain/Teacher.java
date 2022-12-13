package srau.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Entity
@Table
@NoArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "Teacher should have a name")
    private String name;
    @NotBlank(message = "Teacher should have an email")
    @Email(message = "Invalid email")
    private String email;
    @OneToMany
    private Set<Course> courses;

    public Teacher(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
