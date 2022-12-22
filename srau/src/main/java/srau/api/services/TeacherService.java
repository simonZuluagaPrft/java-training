package srau.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import srau.api.domain.*;
import srau.api.exception.BussinesLogicException;
import srau.api.exception.ElementNotFoundException;
import srau.api.exception.ElementTakenException;
import srau.api.mapstruct.dto.GradeGetDto;
import srau.api.mapstruct.dto.GradePostDto;
import srau.api.mapstruct.dto.TeacherGetDto;
import srau.api.mapstruct.dto.TeacherPostDto;
import srau.api.mapstruct.mapper.GradeMapper;
import srau.api.mapstruct.mapper.TeacherMapper;
import srau.api.repositories.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    private final AppUserRepository appUserRepository;
    private final CourseRepository courseRepository;
    private final GradeRepository gradeRepository;
    private final RoleRepository roleRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final GradeMapper gradeMapper;

    @Autowired
    public TeacherService(
            AppUserRepository appUserRepository,
            CourseRepository courseRepository,
            GradeRepository gradeRepository,
            RoleRepository roleRepository,
            StudentRepository studentRepository,
            TeacherRepository teacherRepository,
            TeacherMapper teacherMapper,
            GradeMapper gradeMapper) {
        this.appUserRepository = appUserRepository;
        this.courseRepository = courseRepository;
        this.gradeRepository = gradeRepository;
        this.roleRepository = roleRepository;
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

    public Teacher createTeacher(TeacherPostDto teacherPostDto)
            throws ElementTakenException, ElementNotFoundException {
        String teacherRoleName = "TEACHER";
        Role teacherRole = roleRepository.findByRoleName(teacherRoleName)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No role named " + teacherRoleName));
        AppUser appUser = appUserRepository.findByUsername(teacherPostDto.getUsername())
                        .orElseThrow(() -> new ElementNotFoundException(
                                "No user with username: " + teacherPostDto.getUsername()));

        Optional<Teacher> optionalTeacher = teacherRepository.findByAppUser(appUser);

        if (optionalTeacher.isPresent()) {
            throw new ElementTakenException("There is already a teacher bounded to this user");
        }
        appUser.addRole(teacherRole);
        appUserRepository.save(appUser);

        return teacherRepository.save(new Teacher(appUser));
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
