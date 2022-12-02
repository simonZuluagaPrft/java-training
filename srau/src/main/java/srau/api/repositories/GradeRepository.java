package srau.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import srau.api.domain.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

}
