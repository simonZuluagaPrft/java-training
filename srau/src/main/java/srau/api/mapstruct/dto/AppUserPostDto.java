package srau.api.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AppUserPostDto {
    @NotBlank(message = "username is needed to create user")
    private String username;
    @NotBlank(message = "email is needed to create student")
    @Email(message = "invalid email")
    private String email;
    @NotBlank(message = "password is needed to create student")
    private String password;
}
