package srau.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import srau.api.domain.Course;
import srau.api.domain.Grade;
import srau.api.domain.Student;
import srau.api.domain.Teacher;
import srau.api.exception.BussinesLogicException;
import srau.api.exception.ElementNotFoundException;
import srau.api.exception.ElementTakenException;
import srau.api.mapstruct.dto.GradeGetDto;
import srau.api.mapstruct.dto.GradePostDto;
import srau.api.mapstruct.dto.TeacherGetDto;
import srau.api.mapstruct.dto.TeacherPostDto;
import srau.api.mapstruct.mapper.GradeMapper;
import srau.api.mapstruct.mapper.TeacherMapper;
import srau.api.repositories.CourseRepository;
import srau.api.repositories.GradeRepository;
import srau.api.repositories.StudentRepository;
import srau.api.repositories.TeacherRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    private final CourseRepository courseRepository;
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final GradeMapper gradeMapper;

    @Autowired
    public TeacherService(
            CourseRepository courseRepository,
            GradeRepository gradeRepository,
            StudentRepository studentRepository,
            TeacherRepository teacherRepository,
            TeacherMapper teacherMapper,
            GradeMapper gradeMapper) {
        this.courseRepository = courseRepository;
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
        this.gradeMapper = gradeMapper;
    }

    public List<TeacherGetDto> getTeachers() {
        return teacherRepository
                .findAll()
                .stream()
                .map(teacherMapper::teacherToTeacherGetDto)
                .collect(Collectors.toList());
    }

    public TeacherGetDto getTeacherByEmail(String teacherEmail) throws ElementNotFoundException {
        Teacher teacher = teacherRepository.findTeacherByEmail(teacherEmail)
                .orElseThrow(() ->
                        new ElementNotFoundException("No teacher with email: " + teacherEmail));

        return teacherMapper.teacherToTeacherGetDto(teacher);
    }

    public void createTeacher(TeacherPostDto teacherPostDto) throws ElementTakenException {
        Optional<Teacher> teacherOptional = teacherRepository
                .findTeacherByEmail(teacherPostDto.getEmail());

        if (teacherOptional.isPresent()) {
            throw new ElementTakenException("Email taken");
        }

        teacherRepository.save(teacherMapper.teacherPostDtoToTeacher(teacherPostDto));
    }

    @Transactional
    public TeacherGetDto updateTeacher(Long teacherId, String name, String email)
            throws ElementNotFoundException, ElementTakenException {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No teacher with id: " + teacherId));

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
                throw new ElementTakenException("Email taken");
            }

            teacher.setEmail(email);
        }

        return teacherMapper.teacherToTeacherGetDto(teacher);
    }

    public void deleteTeacher(Long teacherId) throws ElementNotFoundException {
        boolean exists = teacherRepository.existsById(teacherId);

        if (!exists) {
            throw new ElementNotFoundException("No teacher with id: " + teacherId);
        }
        teacherRepository.deleteById(teacherId);
    }

    public void gradeStudent(Long teacherId, GradePostDto gradePostDto)
            throws ElementNotFoundException, BussinesLogicException {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No teacher with id: " + teacherId));

        Course course = courseRepository.findById(gradePostDto.getCourseId())
                .orElseThrow(() -> new ElementNotFoundException(
                        "No course with id: " + gradePostDto.getCourseId()));

        Student student = studentRepository.findById(gradePostDto.getStudentId())
                .orElseThrow(() -> new ElementNotFoundException(
                        "No student with id: " + gradePostDto.getStudentId()));

        if (course.getTeacher() != teacher) {
            throw new BussinesLogicException("Teacher doesn't teach the course");
        }

        if (!course.getStudents().contains(student)) {
            throw new BussinesLogicException("Student is not enrolled in the course");
        }

        Optional<Grade> optGrade = gradeRepository
                .getByCourseIdStudentId(course.getId(), student.getId());
        if (optGrade.isPresent()) {
            throw new BussinesLogicException("The student has been already graded in the course");
        }

        Grade grade = new Grade(gradePostDto.getScore(), student, course);
        gradeRepository.save(grade);
    }

    @Transactional
    public GradeGetDto updateStudentGrade(Long teacherId, GradePostDto gradePostDto)
            throws ElementNotFoundException, BussinesLogicException {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No teacher with id: " + teacherId));

        Course course = courseRepository.findById(gradePostDto.getCourseId())
                .orElseThrow(() -> new ElementNotFoundException(
                        "No course with id: " + gradePostDto.getCourseId()));

        Student student = studentRepository.findById(gradePostDto.getStudentId())
                .orElseThrow(() -> new ElementNotFoundException(
                        "No student with id: " + gradePostDto.getStudentId()));

        if (course.getTeacher() != teacher) {
            throw new BussinesLogicException("Teacher doesn't teach the course");
        }

        if (!course.getStudents().contains(student)) {
            throw new BussinesLogicException("Student is not enrolled in the course");
        }

        Optional<Grade> optGrade = gradeRepository
                .getByCourseIdStudentId(course.getId(), student.getId());
        if (optGrade.isEmpty()) {
            throw new BussinesLogicException("The student has not been graded in the course");
        }

        Grade grade = optGrade.get();
        grade.setScore(gradePostDto.getScore());

        return gradeMapper.gradeToGradeGetDto(grade);
    }
}
