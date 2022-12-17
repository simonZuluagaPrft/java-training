package srau.api.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AuthenticationRequest {
    @NotBlank(message = "A username is needed to login")
    private String username;
    @NotBlank(message = "A password is needed to login")
    private String password;
}
