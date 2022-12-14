package srau.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import srau.api.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
