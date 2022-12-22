package srau.api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import srau.api.domain.*;
import srau.api.mapstruct.dto.*;
import srau.api.repositories.RoleRepository;
import srau.api.services.*;

import java.util.List;

@Configuration
public class InitConfig {
    @Bean
    CommandLineRunner initCommandLineRunner(
            AppUserService appUserService,
            StudentService studentService,
            SubjectService subjectService,
            TeacherService teacherService,
            CourseService courseService,
            LectureService lectureService,
            GradeService gradeService,
            RoleRepository roleRepository) {
        return args -> {
            Role user = new Role("USER");
            Role admin = new Role("ADMIN");
            Role student = new Role("STUDENT");
            Role teacher = new Role("TEACHER");
            roleRepository.saveAll(List.of(user, admin, student, teacher));

            AppUser lucy = appUserService.createAppUser(new AppUserPostDto("lucy", "lucy@gmail.com", "netrunner"));
            AppUser david = appUserService.createAppUser(new AppUserPostDto("david", "david@gmail.com", "cyberpunk"));
            AppUser venus = appUserService.createAppUser(new AppUserPostDto("venus", "venus@gmail.com", "t1"));
            AppUser jupyter = appUserService.createAppUser(new AppUserPostDto("jupyter", "jupyter@gmail.com", "t2"));
            appUserService.addRoleToAppUser(lucy.getId(), admin.getRoleName());

            Student lucyStudent = studentService.createStudent(new StudentPostDto("lucy"));
            Student davidStudent = studentService.createStudent(new StudentPostDto("david"));

            Teacher venusTeacher = teacherService.createTeacher(new TeacherPostDto("venus"));
            Teacher jupyterTeacher = teacherService.createTeacher(new TeacherPostDto("jupyter"));

            Subject math = subjectService.createSubject(new SubjectPostDto("Math", "some numbers"));
            Subject physics = subjectService.createSubject(new SubjectPostDto("Physics", "some physics"));

            Course mathVenus = courseService.createCourse(new CoursePostDto(math.getId(), venusTeacher.getId()));
            Course physicsJupyter = courseService.createCourse(new CoursePostDto(physics.getId(), jupyterTeacher.getId()));
            Course physicsVenus = courseService.createCourse(new CoursePostDto(physics.getId(), venusTeacher.getId()));

            Lecture a1MathVenus = lectureService.createLecture(new LecturePostDto(mathVenus.getId(), 1, 7, 9));
            Lecture a2MathVenus = lectureService.createLecture(new LecturePostDto(mathVenus.getId(), 3, 9, 11));
            Lecture a1physicsJupyter = lectureService.createLecture(new LecturePostDto(physicsJupyter.getId(), 5, 14, 17));

            Grade luneMathVenus = gradeService.createGrade(new GradePostDto(5, lucyStudent.getId(), mathVenus.getId()));
            Grade lunePhysicsJupyter = gradeService.createGrade(new GradePostDto(2, lucyStudent.getId(), physicsJupyter.getId()));
            Grade soleiMathVenus = gradeService.createGrade(new GradePostDto(4, davidStudent.getId(), mathVenus.getId()));
            Grade soleiPhysicsJupyter = gradeService.createGrade(new GradePostDto(5, davidStudent.getId(), physicsJupyter.getId()));

//            courseService.enrollStudent(physicsJupyter.getId(), davidStudent.getId());
//            courseService.enrollStudent(mathVenus.getId(), davidStudent.getId());
//            courseService.enrollStudent(physicsJupyter.getId(), lucyStudent.getId());
//            courseService.enrollStudent(mathVenus.getId(), lucyStudent.getId());
        };
    }
}
