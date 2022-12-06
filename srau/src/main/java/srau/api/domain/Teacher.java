package srau.api.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table
public class Teacher {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;

    @OneToMany
    private Set<Course> courses;

    public Teacher() {
    }

    public Teacher(String name, String email) {
        this.name = name;
        this.email = email;
    }

}
