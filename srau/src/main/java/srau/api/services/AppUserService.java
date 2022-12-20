package srau.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import srau.api.domain.AppUser;
import srau.api.domain.Role;
import srau.api.exception.ElementNotFoundException;
import srau.api.exception.ElementTakenException;
import srau.api.mapstruct.dto.AppUserGetDto;
import srau.api.mapstruct.dto.AppUserPostDto;
import srau.api.mapstruct.mapper.AppUserMapper;
import srau.api.repositories.AppUserRepository;
import srau.api.repositories.RoleRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final AppUserMapper appUserMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AppUserService(
            AppUserRepository appUserRepository,
            RoleRepository roleRepository,
            AppUserMapper appUserMapper,
            BCryptPasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.appUserMapper = appUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser readAppUserByUsername(String username) {
        return appUserRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
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

    public AppUser createAppUser(AppUserPostDto appUserPostDto)
            throws ElementTakenException, ElementNotFoundException {
        String BASE_ROLE_ROLENAME = "user";
        Role baseRole = roleRepository.findByRoleName(BASE_ROLE_ROLENAME)
                .orElseThrow(() -> new ElementNotFoundException("Could not find base role"));

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

        appUserPostDto.setPassword(passwordEncoder.encode(appUserPostDto.getPassword()));
        AppUser appUser = appUserMapper.appUserPostDtoToAppUser(appUserPostDto);
        appUser.addRole(baseRole);
        return appUserRepository.save(appUser);
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
