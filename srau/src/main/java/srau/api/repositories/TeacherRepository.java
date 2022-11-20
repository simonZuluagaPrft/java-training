package srau.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import srau.api.domain.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
