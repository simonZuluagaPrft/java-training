package srau.api.services;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import srau.api.domain.Course;
import srau.api.domain.Student;
import srau.api.domain.Subject;
import srau.api.domain.Teacher;
import srau.api.mapstruct.dto.CoursePostDto;
import srau.api.mapstruct.dto.StudentGetDto;
import srau.api.mapstruct.mapper.StudentMapper;
import srau.api.repositories.CourseRepository;
import srau.api.repositories.StudentRepository;
import srau.api.repositories.SubjectRepository;
import srau.api.repositories.TeacherRepository;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Autowired
    public CourseService(CourseRepository courseRepository, SubjectRepository subjectRepository,
            TeacherRepository teacherRepository, StudentRepository studentRepository, StudentMapper studentMapper) {
        this.courseRepository = courseRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public Course getCourseById(Long courseId) {
        Optional<Course> optCourse = courseRepository.findById(courseId);

        if (!optCourse.isPresent()) {
            throw new IllegalStateException("No course with id: " + courseId);
        }

        return optCourse.get();
    }

    public void createCourse(CoursePostDto coursePostDto) {
        Optional<Subject> optSubject = subjectRepository
                .findById(coursePostDto.getSubjectId());

        if (!optSubject.isPresent()) {
            throw new IllegalStateException(
                    "No subject with id: " + coursePostDto.getSubjectId());
        }

        Subject subject = optSubject.get();

        Optional<Teacher> optTeacher = teacherRepository
                .findById(coursePostDto.getTeacherId());

        if (!optTeacher.isPresent()) {
            throw new IllegalStateException(
                    "No teacher with id: " + coursePostDto.getTeacherId());
        }

        Teacher teacher = optTeacher.get();

        Course course = new Course(subject, teacher);
        courseRepository.save(course);
    }

    @Transactional
    public void changeCourseTeacher(Long courseId, String teacherEmail) {
        Optional<Course> optCourse = courseRepository.findById(courseId);

        if (!optCourse.isPresent()) {
            throw new IllegalStateException("No course with id: " + courseId);
        }

        Course course = optCourse.get();

        Optional<Teacher> optTeacher = teacherRepository
                .findTeacherByEmail(teacherEmail);

        if (!optTeacher.isPresent()) {
            throw new IllegalStateException(
                    "No teacher with email: " + teacherEmail);
        }

        Teacher teacher = optTeacher.get();

        course.setTeacher(teacher);
    }

    public void deleteCourse(Long courseId) {
        boolean exists = courseRepository.existsById(courseId);

        if (!exists) {
            throw new IllegalStateException(
                    "Course with id " + courseId + " does not exists");
        }
        courseRepository.deleteById(courseId);
    }

    public void enrollStudent(Long courseId, Long studentId) {
        Optional<Course> optCourse = courseRepository.findById(courseId);

        if (!optCourse.isPresent()) {
            throw new IllegalStateException("No course with id: " + courseId);
        }

        Course course = optCourse.get();

        Optional<Student> optStudent = studentRepository.findById(studentId);

        if (!optStudent.isPresent()) {
            throw new IllegalStateException("No student with id: " + studentId);
        }

        Student student = optStudent.get();

        course.addStudent(student);
        courseRepository.save(course);
    }

    public void dropStudent(Long courseId, Long studentId) {
        Optional<Course> optCourse = courseRepository.findById(courseId);

        if (!optCourse.isPresent()) {
            throw new IllegalStateException("No course with id: " + courseId);
        }

        Course course = optCourse.get();

        Optional<Student> optStudent = studentRepository.findById(studentId);

        if (!optStudent.isPresent()) {
            throw new IllegalStateException("No student with id: " + studentId);
        }

        Student student = optStudent.get();

        course.deleteStudent(student);
        courseRepository.save(course);
    }

    public Set<StudentGetDto> getCourseStudents(Long courseId) {
        Optional<Course> optCourse = courseRepository.findById(courseId);

        if (!optCourse.isPresent()) {
            throw new IllegalStateException("No course with id: " + courseId);
        }

        Course course = optCourse.get();

        return course
                .getStudents()
                .stream()
                .map(studentMapper::studentToStudentGetDto)
                .collect(Collectors.toSet());
    }

    public Set<StudentGetDto> getCoursePassedStudents(Long courseId) {
        Optional<Course> optCourse = courseRepository.findById(courseId);

        if (!optCourse.isPresent()) {
            throw new IllegalStateException("No course with id: " + courseId);
        }

        Course course = optCourse.get();

        return course
                .getGrades()
                .stream()
                .filter(g -> g.getScore() >= 3)
                .map(g -> studentMapper.studentToStudentGetDto(g.getStudent()))
                .collect(Collectors.toSet());
    }

    public Set<StudentGetDto> getCourseFailedStudents(Long courseId) {
        Optional<Course> optCourse = courseRepository.findById(courseId);

        if (!optCourse.isPresent()) {
            throw new IllegalStateException("No course with id: " + courseId);
        }

        Course course = optCourse.get();

        return course
                .getGrades()
                .stream()
                .filter(g -> g.getScore() < 3)
                .map(g -> studentMapper.studentToStudentGetDto(g.getStudent()))
                .collect(Collectors.toSet());
    }
}
