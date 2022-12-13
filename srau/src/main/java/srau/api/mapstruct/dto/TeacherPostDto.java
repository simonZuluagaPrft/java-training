package srau.api.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TeacherPostDto {
    @NotBlank(message = "username is needed to create student")
    private String username;
}
