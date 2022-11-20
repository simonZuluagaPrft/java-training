package srau.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import srau.api.domain.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
