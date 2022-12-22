package srau.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import srau.api.exception.ElementNotFoundException;
import srau.api.exception.ElementTakenException;
import srau.api.mapstruct.dto.AppUserGetDto;
import srau.api.mapstruct.dto.AppUserPostDto;
import srau.api.services.AppUserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class AppUserController {
    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public ResponseEntity<List<AppUserGetDto>> getAppUsers() {
        return new ResponseEntity<>(appUserService.getAppUsers(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(path = "{username}")
    public ResponseEntity<AppUserGetDto> getAppUserByUsername(
            @PathVariable("username") String username) throws ElementNotFoundException {
        return new ResponseEntity<>(
                appUserService.getAppUserByUsername(username), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping(path = "{username}")
    public ResponseEntity<AppUserGetDto> updateAppUser(
            @PathVariable("username") String username,
            @RequestParam(required = false) String newUsername,
            @RequestParam(required = false) String email)
            throws ElementNotFoundException, ElementTakenException {
        AppUserGetDto appUserGetDto = appUserService.updateAppUser(username, newUsername, email);
        return new ResponseEntity<>(appUserGetDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(path = "{userId}")
    public ResponseEntity<HttpStatus> deleteAppUser(@PathVariable("userId") Long userId)
            throws ElementNotFoundException {
        appUserService.deleteAppUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "signin")
    public ResponseEntity<HttpStatus> createAppUser(
            @RequestBody @Valid AppUserPostDto appUserPostDto)
            throws ElementTakenException, ElementNotFoundException {
        appUserService.createAppUser(appUserPostDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
