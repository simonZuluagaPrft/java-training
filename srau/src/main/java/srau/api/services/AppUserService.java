package srau.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import srau.api.domain.AppUser;
import srau.api.exception.ElementNotFoundException;
import srau.api.exception.ElementTakenException;
import srau.api.mapstruct.dto.AppUserGetDto;
import srau.api.mapstruct.dto.AppUserPostDto;
import srau.api.mapstruct.mapper.AppUserMapper;
import srau.api.repositories.AppUserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, AppUserMapper appUserMapper) {
        this.appUserRepository = appUserRepository;
        this.appUserMapper = appUserMapper;
    }

    public List<AppUserGetDto> getAppUsers() {
        return appUserRepository
                .findAll()
                .stream()
                .map(appUserMapper::appUserToAppUserGetDto)
                .collect(Collectors.toList());
    }

    public AppUserGetDto getAppUserByUsername(String username) throws ElementNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No user with username: " + username));

        return appUserMapper.appUserToAppUserGetDto(appUser);
    }

    public void createAppUser(AppUserPostDto appUserPostDto) throws ElementTakenException {
        Optional<AppUser> appUserByUsername = appUserRepository
                .findByUsername(appUserPostDto.getUsername());

        if (appUserByUsername.isPresent()) {
            throw new ElementTakenException("Username taken");
        }

        Optional<AppUser> appUserByEmail = appUserRepository
                .findByEmail(appUserPostDto.getEmail());

        if (appUserByEmail.isPresent()) {
            throw new ElementTakenException("Email already in use");
        }

        appUserRepository.save(appUserMapper.appUserPostDtoToAppUser(appUserPostDto));
    }

    @Transactional
    public AppUserGetDto updateAppUser(String username, String newUsername, String email)
            throws ElementNotFoundException, ElementTakenException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No user with username: " + username));

        if (newUsername != null &&
                newUsername.length() > 0 &&
                !Objects.equals(appUser.getUsername(), newUsername)) {
            appUser.setUsername(newUsername);
        }

        if (email != null &&
                email.length() > 0 &&
                !Objects.equals(appUser.getEmail(), email)) {

            Optional<AppUser> appUserOptional = appUserRepository
                    .findByEmail(email);

            if (appUserOptional.isPresent()) {
                throw new ElementTakenException("email taken");
            }

            appUser.setEmail(email);
        }

        return appUserMapper.appUserToAppUserGetDto(appUser);
    }

    public void deleteAppUser(Long userId) throws ElementNotFoundException {
        boolean exists = appUserRepository.existsById(userId);

        if (!exists) {
            throw new ElementNotFoundException("No user with id: " + userId);
        }

        appUserRepository.deleteById(userId);
    }
}
