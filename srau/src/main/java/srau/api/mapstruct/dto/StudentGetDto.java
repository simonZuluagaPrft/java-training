package srau.api.mapstruct.dto;

import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentGetDto {
    @Id
    private Long id;
    private String name;
    private String email;
}