package srau.api.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import srau.api.domain.AppUser;
import srau.api.domain.Role;
import srau.api.exception.ElementNotFoundException;
import srau.api.exception.ElementTakenException;
import srau.api.mapstruct.dto.AppUserPostDto;
import srau.api.mapstruct.mapper.AppUserMapper;
import srau.api.repositories.AppUserRepository;
import srau.api.repositories.RoleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AppUserServiceTest {
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    String testUsername = "testUsername";
    String testEmail = "test@email";
    String testPasswd = "testPasswd";
    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private AppUserMapper appUserMapper;
    private AutoCloseable autoCloseable;
    private AppUserService underTest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new AppUserService(
                appUserRepository,
                roleRepository,
                appUserMapper,
                bCryptPasswordEncoder);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void shouldReadAppUserByUsername() {
        // given
        doReturn(Optional
                .of(new AppUser(
                        testUsername,
                        testEmail,
                        testPasswd)))
                .when(appUserRepository).findByUsername(testUsername);
        // when
        underTest.readAppUserByUsername(testUsername);
        // then
        verify(appUserRepository).findByUsername(testUsername);
    }

    @Test
    void shouldNotReadAppUserByUsername() {
        // given
        // empty table
        // then
        assertThrows(
                EntityNotFoundException.class,
                () -> underTest.readAppUserByUsername(testUsername));
    }

    @Test
    void getAppUsers() {
        // when
        underTest.getAppUsers();
        // then
        verify(appUserRepository, times(1)).findAll();
    }

    @Test
    void shouldCreateAppUser() throws ElementNotFoundException, ElementTakenException {
        // given
        Role baseRole = new Role("USER");
        AppUserPostDto appUserPostDto = new AppUserPostDto(
                testUsername,
                testEmail,
                testPasswd);
        AppUser appUser = new AppUser(testUsername, testEmail, testPasswd);

        doReturn(Optional.of(baseRole))
                .when(roleRepository).findByRoleName("USER");
        doReturn(appUser)
                .when(appUserMapper).appUserPostDtoToAppUser(appUserPostDto);
        // when
        underTest.createAppUser(appUserPostDto);
        // then
        InOrder orderVerifier = inOrder(
                roleRepository,
                appUserRepository,
                appUserMapper);
        orderVerifier.verify(roleRepository).findByRoleName("USER");
        orderVerifier.verify(appUserRepository).findByUsername(testUsername);
        orderVerifier.verify(appUserRepository).findByEmail(testEmail);
        orderVerifier.verify(appUserMapper).appUserPostDtoToAppUser(appUserPostDto);
        orderVerifier.verify(appUserRepository).save(appUser);
    }

    @Test
    void shouldThrowBaseRoleNotFound() {
        // given
        AppUserPostDto appUserPostDto = new AppUserPostDto(
                testUsername,
                testEmail,
                testPasswd);
        // when
        ElementNotFoundException thrown = assertThrows(ElementNotFoundException.class,
                () -> underTest.createAppUser(appUserPostDto),
                "Could not find base role");
        // then
        assertTrue(thrown.getMessage().contentEquals("Could not find base role"));
    }

    @Test
    void shouldThrowUsernameTaken() {
        // given
        String userNameTakenMsg = "Username taken";
        Role baseRole = new Role("USER");
        AppUser appUser = new AppUser(testUsername, testEmail, testPasswd);
        AppUserPostDto appUserPostDto = new AppUserPostDto(
                testUsername,
                testEmail,
                testPasswd);
        doReturn(Optional.of(baseRole))
                .when(roleRepository).findByRoleName("USER");
        doReturn(Optional.of(appUser)).when(appUserRepository).findByUsername(testUsername);
        // when
        ElementTakenException thrown = assertThrows(ElementTakenException.class,
                () -> underTest.createAppUser(appUserPostDto),
                userNameTakenMsg);
        // then
        assertTrue(thrown.getMessage().contentEquals(userNameTakenMsg));
    }

    @Test
    void shouldThrowEmailTaken() {
        // given
        String emailTakenMsg = "Email already in use";
        Role baseRole = new Role("USER");
        AppUser appUser = new AppUser(testUsername, testEmail, testPasswd);
        AppUserPostDto appUserPostDto = new AppUserPostDto(
                testUsername,
                testEmail,
                testPasswd);
        doReturn(Optional.of(baseRole))
                .when(roleRepository).findByRoleName("USER");
        doReturn(Optional.of(appUser)).when(appUserRepository).findByEmail(testEmail);
        // when
        ElementTakenException thrown = assertThrows(ElementTakenException.class,
                () -> underTest.createAppUser(appUserPostDto),
                emailTakenMsg);
        // then
        assertTrue(thrown.getMessage().contentEquals(emailTakenMsg));
    }

    @Test
    @Disabled
    void updateAppUser() {
    }

    @Test
    @Disabled
    void deleteAppUser() {
    }

    @Test
    @Disabled
    void addRoleToAppUser() {
    }
}