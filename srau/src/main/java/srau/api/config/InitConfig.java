package srau.api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import srau.api.domain.*;
import srau.api.mapstruct.dto.AppUserPostDto;
import srau.api.repositories.*;
import srau.api.services.AppUserService;

import java.time.DayOfWeek;
import java.util.List;

@Configuration
public class InitConfig {
    @Bean
    CommandLineRunner initCommandLineRunner(
            AppUserRepository appUserRepository,
            StudentRepository studentRepository,
            SubjectRepository subjectRepository,
            TeacherRepository teacherRepository,
            CourseRepository courseRepository,
            LectureRepository lectureRepository,
            GradeRepository gradeRepository,
            RoleRepository roleRepository) {
        return args -> {
//            Role user = new Role("user");
//            Role admin = new Role("admin");
//            roleRepository.saveAll(List.of(user, admin));

            AppUser lucy = new AppUser("lucy", "lucy@gmail.com", "netrunner");
            AppUser david = new AppUser("david", "david@gmail.com", "cyberpunk");
            AppUser venus = new AppUser("venus", "venus@gmail.com", "t1");
            AppUser jupyter = new AppUser("jupyter", "jupyter@gmail.com", "t2");
            appUserRepository.saveAll(List.of(lucy, david, venus, jupyter));

            Student lucyStudent = new Student(lucy);
            Student davidStudent = new Student(david);
            studentRepository.saveAll(List.of(lucyStudent, davidStudent));

            Teacher venusTeacher = new Teacher(venus);
            Teacher jupyterTeacher = new Teacher(jupyter);
            teacherRepository.saveAll(List.of(venusTeacher, jupyterTeacher));

            Subject math = new Subject("Math", "some numbers");
            Subject physics = new Subject("Physics", "some physics");
            subjectRepository.saveAll(List.of(math, physics));

            Course mathVenus = new Course(math, venusTeacher);
            Course physicsJupyter = new Course(physics, jupyterTeacher);
            Course physicsVenus = new Course(physics, venusTeacher);
            courseRepository.saveAll(List.of(mathVenus, physicsJupyter, physicsVenus));

            Lecture a1MathVenus = new Lecture(DayOfWeek.of(1), 7, 9, mathVenus);
            Lecture a2MathVenus = new Lecture(DayOfWeek.of(3), 9, 11, mathVenus);
            Lecture a1physicsJupyter = new Lecture(DayOfWeek.of(5), 14, 17, physicsJupyter);
            lectureRepository.saveAll(List.of(a1MathVenus, a2MathVenus, a1physicsJupyter));

            Grade luneMathVenus = new Grade(5, lucyStudent, mathVenus);
            Grade lunePhysicsJupyter = new Grade(2, lucyStudent, physicsJupyter);
            Grade soleiMathVenus = new Grade(4, davidStudent, mathVenus);
            Grade soleiPhysicsJupyter = new Grade(5, davidStudent, physicsJupyter);
            gradeRepository.saveAll(List.of(
                    luneMathVenus, lunePhysicsJupyter, soleiMathVenus, soleiPhysicsJupyter));
        };
    }
}
