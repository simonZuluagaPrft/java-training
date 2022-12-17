package srau.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import srau.api.domain.Course;
import srau.api.domain.Student;
import srau.api.domain.Subject;
import srau.api.domain.Teacher;
import srau.api.exception.BussinesLogicException;
import srau.api.exception.ElementNotFoundException;
import srau.api.mapstruct.dto.CourseGetDto;
import srau.api.mapstruct.dto.CoursePostDto;
import srau.api.mapstruct.dto.StudentGetDto;
import srau.api.mapstruct.mapper.CourseMapper;
import srau.api.mapstruct.mapper.StudentMapper;
import srau.api.repositories.CourseRepository;
import srau.api.repositories.StudentRepository;
import srau.api.repositories.SubjectRepository;
import srau.api.repositories.TeacherRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CourseMapper courseMapper;
    private final StudentMapper studentMapper;

    @Autowired
    public CourseService(
            CourseRepository courseRepository,
            SubjectRepository subjectRepository,
            StudentRepository studentRepository,
            TeacherRepository teacherRepository,
            CourseMapper courseMapper,
            StudentMapper studentMapper) {
        this.courseRepository = courseRepository;
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.courseMapper = courseMapper;
        this.studentMapper = studentMapper;
    }

    public List<CourseGetDto> getCourses() {
        return courseRepository
                .findAll()
                .stream()
                .map(courseMapper::courseToCourseGetDto)
                .collect(Collectors.toList());
    }

    public CourseGetDto getCourseById(Long courseId) throws ElementNotFoundException {
        Course course =courseRepository.findById(courseId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No course with id: " + courseId));

        return courseMapper.courseToCourseGetDto(course);
    }

    public Course createCourse(CoursePostDto coursePostDto) throws ElementNotFoundException {
        Subject subject = subjectRepository.findById(coursePostDto.getSubjectId())
                .orElseThrow(() -> new ElementNotFoundException(
                        "No subject with id: " + coursePostDto.getSubjectId()));

        Teacher teacher = teacherRepository.findById(coursePostDto.getTeacherId())
                .orElseThrow(() -> new ElementNotFoundException(
                        "No teacher with id: " + coursePostDto.getTeacherId()));

        Course course = new Course(subject, teacher);
        return courseRepository.save(course);
    }

    @Transactional
    public CourseGetDto changeCourseTeacher(Long courseId, Long teacherId)
            throws ElementNotFoundException {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No course with id: " + courseId));

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No teacher with email: " + teacherId));

        course.setTeacher(teacher);
        return courseMapper.courseToCourseGetDto(course);
    }

    public void deleteCourse(Long courseId) throws ElementNotFoundException {
        boolean exists = courseRepository.existsById(courseId);

        if (!exists) {
            throw new ElementNotFoundException("Course with id " + courseId + " does not exists");
        }
        courseRepository.deleteById(courseId);
    }

    public void enrollStudent(Long courseId, Long studentId)
            throws ElementNotFoundException, BussinesLogicException {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No course with id: " + courseId));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No student with id: " + studentId));

        if (course
                .getTeacher()
                .getAppUser()
                .getUsername()
                .equals(student.getAppUser().getUsername())) {
            throw new BussinesLogicException(
                    "A student cannot be enrolled in a course in which he himself is the teacher");
        }

        course.addStudent(student);
        courseRepository.save(course);
    }

    public void dropStudent(Long courseId, Long studentId) throws ElementNotFoundException {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No course with id: " + courseId));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No student with id: " + studentId));

        course.deleteStudent(student);
        courseRepository.save(course);
    }

    public List<StudentGetDto> getCourseStudents(Long courseId) throws ElementNotFoundException {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No course with id: " + courseId));

        return course
                .getStudents()
                .stream()
                .map(studentMapper::studentToStudentGetDto)
                .collect(Collectors.toList());
    }

    public List<StudentGetDto> getCoursePassedStudents(Long courseId)
            throws ElementNotFoundException {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No course with id: " + courseId));

        return course
                .getGrades()
                .stream()
                .filter(g -> g.getScore() >= 3)
                .map(g -> studentMapper.studentToStudentGetDto(g.getStudent()))
                .collect(Collectors.toList());
    }

    public List<StudentGetDto> getCourseFailedStudents(Long courseId)
            throws ElementNotFoundException {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No course with id: " + courseId));

        return course
                .getGrades()
                .stream()
                .filter(g -> g.getScore() < 3)
                .map(g -> studentMapper.studentToStudentGetDto(g.getStudent()))
                .collect(Collectors.toList());
    }
}
