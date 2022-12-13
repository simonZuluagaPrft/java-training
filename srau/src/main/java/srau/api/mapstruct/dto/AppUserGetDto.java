package srau.api.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class AppUserGetDto {
    @Id
    private Long id;
    private String username;
    private String email;
}
