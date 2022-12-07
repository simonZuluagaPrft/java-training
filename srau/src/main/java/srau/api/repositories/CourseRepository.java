package srau.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import srau.api.domain.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
