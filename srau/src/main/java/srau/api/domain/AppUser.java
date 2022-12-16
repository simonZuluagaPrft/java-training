package srau.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Table
@NoArgsConstructor
public class AppUser implements UserDetails {
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
    @ManyToMany
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
    private Set<Role> roles;

    public AppUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void addRole(Role role) {
        this.roles.add(role);
        role.getAppUsers().add(this);
    }

    @Override
    public List<SimpleGrantedAuthority> getAuthorities() {
//        return getRoles()
//                .stream()
//                .map(r -> new SimpleGrantedAuthority(r.getRoleName()))
//                .collect(Collectors.toList());
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}