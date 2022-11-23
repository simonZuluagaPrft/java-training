package srau.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import srau.api.domain.Course;
import srau.api.domain.Subject;
import srau.api.domain.Teacher;
import srau.api.mapstruct.dto.CoursePostDto;
import srau.api.repositories.CourseRepository;
import srau.api.repositories.SubjectRepository;
import srau.api.repositories.TeacherRepository;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, SubjectRepository subjectRepository,
            TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
    }
    
    public Course getCourseById(Long courseId) {
        Optional<Course> optCourse = courseRepository
                .findById(courseId);

        if (!optCourse.isPresent()) {
            throw new IllegalStateException(
                    "No course with email: " + courseId);
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

}
