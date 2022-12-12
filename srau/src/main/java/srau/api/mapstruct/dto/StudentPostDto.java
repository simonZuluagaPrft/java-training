package srau.api.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StudentPostDto {
    @NotBlank(message = "name is needed to create student")
    private String name;
    @NotBlank(message = "email is needed to create student")
    @Email(message = "invalid email")
    private String email;
}
