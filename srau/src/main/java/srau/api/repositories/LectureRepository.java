package srau.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import srau.api.domain.Lecture;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
