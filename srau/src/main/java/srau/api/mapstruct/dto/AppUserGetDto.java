package srau.api.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.util.Set;

@Getter
@Setter
public class AppUserGetDto {
    @Id
    private Long id;
    private String username;
    private String email;
    private Set<RoleGetDto> roles;
}
