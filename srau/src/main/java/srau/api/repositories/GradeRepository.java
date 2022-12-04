package srau.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import srau.api.domain.Grade;

import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    @Query("SELECT g FROM Grade g WHERE g.course.id = ?1 AND g.student.id = ?2")
    Optional<Grade> getByCourseIdStudentId(Long courseId, Long studentId);
}
