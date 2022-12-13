package srau.api.mapstruct.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import srau.api.domain.AppUser;
import srau.api.mapstruct.dto.AppUserGetDto;
import srau.api.mapstruct.dto.AppUserPostDto;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    AppUserGetDto appUserToAppUserGetDto(AppUser appUser);

    @Mapping(target = "id", ignore = true)
    AppUser appUserPostDtoToAppUser(AppUserPostDto appUserPostDto);
}
