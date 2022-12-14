package srau.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table
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
//    @ManyToMany
//    private Set<Role> roles;

    public AppUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}