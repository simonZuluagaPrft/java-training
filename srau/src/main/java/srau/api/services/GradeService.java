package srau.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import srau.api.domain.Course;
import srau.api.domain.Grade;
import srau.api.domain.Student;
import srau.api.mapstruct.dto.GradeGetDto;
import srau.api.mapstruct.dto.GradePostDto;
import srau.api.mapstruct.mapper.GradeMapper;
import srau.api.repositories.CourseRepository;
import srau.api.repositories.GradeRepository;
import srau.api.repositories.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final GradeMapper gradeMapper;

    @Autowired
    public GradeService(
            GradeRepository gradeRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            GradeMapper gradeMapper) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.gradeMapper = gradeMapper;
    }

    public List<GradeGetDto> getGrades() {
        List<Grade> grades = gradeRepository.findAll();

        return grades
                .stream()
                .map(gradeMapper::gradeToGradeGetDto)
                .collect(Collectors.toList());
    }

    public void createGrade(GradePostDto gradePostDto) {
        Student student = studentRepository
                .findById(gradePostDto.getStudentId())
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id " + gradePostDto.getStudentId() + "does not exists"));

        Course course = courseRepository
                .findById(gradePostDto.getCourseId())
                .orElseThrow(() -> new IllegalStateException(
                        "Course with id " + gradePostDto.getCourseId() + "does not exists"));

        Grade grade = new Grade(gradePostDto.getScore(), student, course);

        gradeRepository.save(grade);
    }

    public void deleteGrade(Long gradeId) {
        gradeRepository.deleteById(gradeId);
    }
}
