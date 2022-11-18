package srau.api.subject;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import srau.api.course.Course;

@Data
@Entity
@Table
public class Subject {
    
    @Id
    @SequenceGenerator(
        name = "subject_sequence",
        sequenceName = "subject_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "subject_sequence"
    )
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "subject")
    private Set<Course> courses;

    public Subject() {
    }

    public Subject(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
