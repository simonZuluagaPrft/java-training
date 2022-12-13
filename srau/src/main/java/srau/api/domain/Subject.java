package srau.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Entity
@Table
@NoArgsConstructor
public class Subject {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "Subject should have a name")
    private String name;
    @Size(max = 512, message = "Description should be at max 512 characters")
    private String description;
    @OneToMany
    private Set<Course> courses;

    public Subject(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
