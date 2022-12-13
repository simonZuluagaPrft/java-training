package srau.api.domain;

import java.util.Set;

import javax.persistence.*;

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
    @OneToOne
    private AppUser appUser;
    @OneToMany(mappedBy = "student")
    private Set<Grade> grades;
    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;

    public Student(AppUser appUser) {
        this.appUser = appUser;
    }
}
