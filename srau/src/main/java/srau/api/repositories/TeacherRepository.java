package srau.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import srau.api.domain.AppUser;
import srau.api.domain.Teacher;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT t FROM Teacher t WHERE t.appUser = ?1")
    Optional<Teacher> findByAppUser(AppUser appUser);
}
