package srau.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import srau.api.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
