package srau.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import srau.api.domain.AppUser;
import srau.api.domain.Grade;
import srau.api.domain.Lecture;
import srau.api.domain.Student;
import srau.api.exception.ElementNotFoundException;
import srau.api.exception.ElementTakenException;
import srau.api.mapstruct.dto.*;
import srau.api.mapstruct.mapper.CourseMapper;
import srau.api.mapstruct.mapper.StudentMapper;
import srau.api.mapstruct.mapper.SubjectMapper;
import srau.api.repositories.AppUserRepository;
import srau.api.repositories.GradeRepository;
import srau.api.repositories.StudentRepository;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final AppUserRepository appUserRepository;
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final CourseMapper courseMapper;
    private final StudentMapper studentMapper;
    private final SubjectMapper subjectMapper;

    @Autowired
    public StudentService(
            AppUserRepository appUserRepository,
            GradeRepository gradeRepository,
            StudentRepository studentRepository,
            CourseMapper courseMapper,
            StudentMapper studentMapper,
            SubjectMapper subjectMapper) {
        this.appUserRepository = appUserRepository;
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.courseMapper = courseMapper;
        this.studentMapper = studentMapper;
        this.subjectMapper = subjectMapper;
    }

    public List<StudentGetDto> getStudents() {
        return studentRepository
                .findAll()
                .stream()
                .map(studentMapper::studentToStudentGetDto)
                .collect(Collectors.toList());
    }
    public void createStudent(StudentPostDto studentPostDto)
            throws ElementTakenException, ElementNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(studentPostDto.getUsername())
                .orElseThrow(() -> new ElementNotFoundException(
                        "No user with username: " + studentPostDto.getUsername()));

        Optional<Student> optionalStudent = studentRepository.findByAppUser(appUser);

        if (optionalStudent.isPresent()) {
            throw new ElementTakenException("There is already a student bounded to this user");
        }

        studentRepository.save(new Student(appUser));
    }

    public void deleteStudent(Long studentId) throws ElementNotFoundException {
        boolean exists = studentRepository.existsById(studentId);

        if (!exists) {
            throw new ElementNotFoundException("No student with id " + studentId);
        }

        studentRepository.deleteById(studentId);
    }

    public List<CourseGetDto> getStudentCourses(Long studentId) throws ElementNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No student with id: " + studentId));

        return student
                .getCourses()
                .stream()
                .map(courseMapper::courseToCourseGetDto)
                .collect(Collectors.toList());
    }

    public Integer getStudentGrade(Long studentId, Long courseId) throws ElementNotFoundException {
        Grade grade = gradeRepository.getByCourseIdStudentId(courseId, studentId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "This student has not been graded in the course"));

        return grade.getScore();
    }

    public List<SubjectGetDto> getStudentSubjects(Long studentId) throws ElementNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No student with id: " + studentId));

        return student.getCourses()
                .stream()
                .map(c -> subjectMapper.subjectToSubjectGetDto(c.getSubject()))
                .collect(Collectors.toList());
    }

    public List<Schedule> getStudentSchedule(Long studentId) throws ElementNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No student with id: " + studentId));

        List<List<Lecture>> lectureList = student
                .getCourses()
                .stream()
                .map(c -> c.getLectures().stream().toList())
                .toList();

        List<Schedule> scheduleList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            scheduleList.add(new Schedule(DayOfWeek.of(i + 1).name()));
        }

        lectureList.forEach(
                list -> list.forEach(
                        l -> scheduleList
                                .get(l.getDayOfWeek().getValue() - 1)
                                .getClasses()
                                .add(Pair.of(l.getStartHour(), l.getFinishHour()))
                )
        );

        return scheduleList;
    }

    public List<Report> getStudentReportCard(Long studentId) throws ElementNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No student with id: " + studentId));

        return student
                .getGrades()
                .stream()
                .map(g -> new Report(
                        g.getCourse().getSubject().getName(),
                        g.getCourse().getTeacher().getAppUser().getUsername(),
                        g.getScore()))
                .collect(Collectors.toList());
    }
}
