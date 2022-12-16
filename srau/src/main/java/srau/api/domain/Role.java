package srau.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.jackson2.SimpleGrantedAuthorityMixin;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Entity
@Table
@NoArgsConstructor
public class Role {
    @Id
    private Long id;
    @NotBlank(message = "Role needs a name to be identified with")
    @Size(max = 20)
    private String roleName;
    @ManyToMany(mappedBy = "roles")
    private Set<AppUser> appUsers;

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
