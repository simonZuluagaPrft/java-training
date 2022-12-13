package srau.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table
@NoArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private AppUser appUser;
    @OneToMany
    private Set<Course> courses;

    public Teacher(AppUser appUser) {
        this.appUser = appUser;
    }
}
