package srau.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import srau.api.config.JwtUtil;
import srau.api.exception.ElementNotFoundException;
import srau.api.exception.ElementTakenException;
import srau.api.mapstruct.dto.AppUserGetDto;
import srau.api.mapstruct.dto.AppUserPostDto;
import srau.api.mapstruct.dto.AuthenticationRequest;
import srau.api.services.AppUserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class AppUserController {
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AppUserController(AppUserService appUserService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.appUserService = appUserService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    // TODO: REVIEW CASCADE

    @GetMapping
    public ResponseEntity<List<AppUserGetDto>> getAppUsers() {
        return new ResponseEntity<>(appUserService.getAppUsers(), HttpStatus.OK);
    }

    @GetMapping(path = "{username}")
    public ResponseEntity<AppUserGetDto> getAppUserByUsername(
            @PathVariable("username") String username) throws ElementNotFoundException {
        return new ResponseEntity<>(
                appUserService.getAppUserByUsername(username), HttpStatus.OK);
    }

    @PutMapping(path = "{username}")
    public ResponseEntity<AppUserGetDto> updateAppUser(
            @PathVariable("username") String username,
            @RequestParam(required = false) String newUsername,
            @RequestParam(required = false) String email)
            throws ElementNotFoundException, ElementTakenException {
        AppUserGetDto appUserGetDto = appUserService.updateAppUser(username, newUsername, email);
        return new ResponseEntity<>(appUserGetDto, HttpStatus.OK);
    }

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

    @PostMapping(path = "login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));
        final UserDetails userDetails = appUserService.loadUserByUsername(request.getUsername());
        if (userDetails != null) {
            return new ResponseEntity<>(jwtUtil.generateToken(userDetails), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
