package srau.api.mapstruct.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RoleAddition {
    @NotBlank
    private String roleName;
}
