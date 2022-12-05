package srau.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import srau.api.domain.Grade;
import srau.api.domain.Student;
import srau.api.mapstruct.dto.CourseGetDto;
import srau.api.mapstruct.dto.SubjectGetDto;
import srau.api.mapstruct.mapper.CourseMapper;
import srau.api.mapstruct.mapper.SubjectMapper;
import srau.api.repositories.GradeRepository;
import srau.api.repositories.StudentRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;
    private final CourseMapper courseMapper;
    private final SubjectMapper subjectMapper;

    @Autowired
    public StudentService(StudentRepository studentRepository, GradeRepository gradeRepository, CourseMapper courseMapper, SubjectMapper subjectMapper) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
        this.courseMapper = courseMapper;
        this.subjectMapper = subjectMapper;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentByEmail(String studentEmail) {
        Optional<Student> optStudent = studentRepository
                .findStudentByEmail(studentEmail);

        if (optStudent.isEmpty()) {
            throw new IllegalStateException(
                    "No student with email: " + studentEmail);
        }

        return optStudent.get();
    }

    public void createStudent(Student student) {
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());

        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email taken");
        }
        studentRepository.save(student);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "student with id " + studentId + " does not exists"));

        if (name != null &&
                name.length() > 0 &&
                !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null &&
                email.length() > 0 &&
                !Objects.equals(student.getEmail(), email)) {

            Optional<Student> studentOptional = studentRepository
                    .findStudentByEmail(email);

            if (studentOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            }

            student.setEmail(email);
        }
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);

        if (!exists) {
            throw new IllegalStateException(
                    "Student with id " + studentId + " does not exists");
        }
        
        studentRepository.deleteById(studentId);
    }

    public List<CourseGetDto> getStudentCourses(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "student with id " + studentId + " does not exists"));

        return student
                .getCourses()
                .stream()
                .map(courseMapper::courseToCourseGetDto)
                .collect(Collectors.toList());
    }

    public Integer getStudentGrade(Long studentId, Long courseId) {
        Optional<Grade> optGrade = gradeRepository
                .getByCourseIdStudentId(courseId, studentId);
        if (optGrade.isEmpty()) {
            throw new IllegalStateException(
                    "this student has not been graded in the course");
        }

        Grade grade = optGrade.get();
        return grade.getScore();
    }

    public List<SubjectGetDto> getStudentSubjects(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "student with id " + studentId + " does not exists"));

        return student.getCourses()
                .stream()
                .map(c -> subjectMapper
                        .subjectToSubjectGetDto(c.getSubject()))
                .collect(Collectors.toList());
    }
}
