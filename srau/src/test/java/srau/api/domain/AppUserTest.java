package srau.api.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AppUserTest {
    @Mock
    Role role = new Role("testRole");
    private AppUser underTest;

    @BeforeEach
    void setup() {
        underTest = new AppUser("testUser", "test@user.com", "testpasswd");
    }

    @Test
    void canAddRole() {
        // when
        underTest.addRole(role);
        // then
        assertTrue(underTest.getRoles().contains(role));
    }
}