package srau.api.domain;

import javax.persistence.*;

import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table
public class Subject {
    
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @OneToMany
    private Set<Course> courses;

    public Subject() {
    }

    public Subject(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
