package srau.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import srau.api.domain.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT t FROM Teacher t WHERE t.email = ?1")
    Optional<Teacher> findTeacherByEmail(String teacherEmail);
}
