package srau.api.mapstruct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectPostDto {
    @NotBlank(message = "name is needed to create subject")
    private String name;
    @NotBlank(message = "description is needed to create subject")
    @Size(max = 512, message = "description can not be longer than 512 characters")
    private String description;
}