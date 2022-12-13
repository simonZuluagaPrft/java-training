package srau.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import srau.api.domain.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
