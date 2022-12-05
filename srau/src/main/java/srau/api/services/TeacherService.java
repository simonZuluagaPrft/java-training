package srau.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import srau.api.domain.Course;
import srau.api.domain.Grade;
import srau.api.domain.Student;
import srau.api.domain.Teacher;
import srau.api.mapstruct.dto.GradePostDto;
import srau.api.repositories.CourseRepository;
import srau.api.repositories.GradeRepository;
import srau.api.repositories.StudentRepository;
import srau.api.repositories.TeacherRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository, CourseRepository courseRepository, StudentRepository studentRepository, GradeRepository gradeRepository) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
    }

    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacherByEmail(String teacherEmail) {
        Optional<Teacher> optTeacher = teacherRepository
                .findTeacherByEmail(teacherEmail);

        if (optTeacher.isEmpty()) {
            throw new IllegalStateException(
                    "No teacher with email: " + teacherEmail);
        }

        return optTeacher.get();
    }

    public void createTeacher(Teacher teacher) {
        Optional<Teacher> teacherOptional = teacherRepository
                .findTeacherByEmail(teacher.getEmail());

        if (teacherOptional.isPresent()) {
            throw new IllegalStateException("Email taken");
        }
        teacherRepository.save(teacher);
    }

    @Transactional
    public void updateTeacher(Long teacherId, String name, String email) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalStateException(
                        "teacher with id " + teacherId + " does not exists"));

        if (name != null &&
                name.length() > 0 &&
                !Objects.equals(teacher.getName(), name)) {
            teacher.setName(name);
        }

        if (email != null &&
                email.length() > 0 &&
                !Objects.equals(teacher.getEmail(), email)) {

            Optional<Teacher> teacherOptional = teacherRepository
                    .findTeacherByEmail(email);

            if (teacherOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            }

            teacher.setEmail(email);
        }
    }

    public void deleteTeacher(Long teacherId) {
        boolean exists = teacherRepository.existsById(teacherId);

        if (!exists) {
            throw new IllegalStateException(
                    "Teacher with id " + teacherId + " does not exists");
        }
        teacherRepository.deleteById(teacherId);
    }

    public void gradeStudent(Long teacherId, GradePostDto gradePostDto) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalStateException(
                        "teacher with id " + teacherId + " does not exists"));

        Course course = courseRepository.findById(gradePostDto.getCourseId())
                .orElseThrow(() -> new IllegalStateException(
                        "course with id " + gradePostDto.getCourseId() + " does not exists"));

        Student student = studentRepository.findById(gradePostDto.getStudentId())
                .orElseThrow(() -> new IllegalStateException(
                        "student with id " + gradePostDto.getStudentId() + " does not exists"));

        if (course.getTeacher() != teacher) {
            throw new IllegalStateException(
                    "teacher doesn't teach course with id: " + course.getId());
        }

        if (!course.getStudents().contains(student)) {
            throw new IllegalStateException(
                    "student with id: " + student.getId() + " is not enrolled in that course");
        }

        Optional<Grade> optGrade = gradeRepository.getByCourseIdStudentId(course.getId(), student.getId());
        if (optGrade.isPresent()) {
            throw new IllegalStateException(
                    "this student has been already graded in the course");
        }

        Grade grade = new Grade(gradePostDto.getScore(), student, course);
        gradeRepository.save(grade);
    }

    @Transactional
    public void updateGrade(Long teacherId, GradePostDto gradePostDto) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalStateException(
                        "teacher with id " + teacherId + " does not exists"));

        Course course = courseRepository
                .findById(gradePostDto.getCourseId())
                .orElseThrow(() -> new IllegalStateException(
                        "course with id " + gradePostDto.getCourseId() + " does not exists"));

        Student student = studentRepository
                .findById(gradePostDto.getStudentId())
                .orElseThrow(() -> new IllegalStateException(
                        "student with id " + gradePostDto.getStudentId() + " does not exists"));

        if (course.getTeacher() != teacher) {
            throw new IllegalStateException(
                    "teacher doesn't teach course with id: " + course.getId());
        }

        if (!course.getStudents().contains(student)) {
            throw new IllegalStateException(
                    "student with id: " + student.getId() + " is not enrolled in that course");
        }

        Optional<Grade> optGrade = gradeRepository
                .getByCourseIdStudentId(course.getId(), student.getId());
        if (optGrade.isEmpty()) {
            throw new IllegalStateException(
                    "this student has not been graded in the course");
        }

        Grade grade = optGrade.get();
        grade.setScore(gradePostDto.getScore());
    }
}
