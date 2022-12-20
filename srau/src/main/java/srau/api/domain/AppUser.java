package srau.api.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table
@EqualsAndHashCode(exclude = "roles")
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue
    private Long Id;
    @NotBlank(message = "AppUser should have a username")
    @Column(unique = true)
    private String username;
    @NotBlank(message = "AppUser should have a name")
    @Email(message = "AppUser invalid email")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "AppUser should have a password")
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "app_user_role",
            joinColumns = @JoinColumn(
                    name = "app_user_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"
            )
    )
    private Set<Role> roles = new HashSet<>();

    public AppUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void addRole(Role role) {
        this.roles.add(role);
        role.getAppUsers().add(this);
    }
}