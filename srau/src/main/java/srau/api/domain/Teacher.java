package srau.api.domain;

import javax.persistence.*;

import ch.qos.logback.core.CoreConstants;
import lombok.Data;

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
