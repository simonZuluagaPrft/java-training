package srau.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import srau.api.domain.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);
}
