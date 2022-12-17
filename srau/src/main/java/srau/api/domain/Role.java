package srau.api.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table
@EqualsAndHashCode(exclude = "appUsers")
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "Role needs a name to be identified with")
    @Size(max = 20)
    private String roleName;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<AppUser> appUsers = new HashSet<>();

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
