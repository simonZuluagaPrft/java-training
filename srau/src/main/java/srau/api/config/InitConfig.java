package srau.api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import srau.api.domain.*;
import srau.api.repositories.*;

import java.time.DayOfWeek;
import java.util.List;

@Configuration
public class InitConfig {
    @Bean
    CommandLineRunner initCommandLineRunner(
            StudentRepository studentRepository,
            SubjectRepository subjectRepository,
            TeacherRepository teacherRepository,
            CourseRepository courseRepository,
            LectureRepository lectureRepository,
            GradeRepository gradeRepository) {
        return args -> {
            Student lune = new Student("Lune", "lune@gmail.com");
            Student solei = new Student("Solei", "solei@gmail.com");
            studentRepository.saveAll(List.of(lune, solei));

            Subject math = new Subject("Math", "some numbers");
            Subject physics = new Subject("Physics", "some physics");
            subjectRepository.saveAll(List.of(math, physics));

            Teacher venus = new Teacher("Venus", "venus@gmail.com");
            Teacher jupyter = new Teacher("Jupyter", "jupyter@gmail.com");
            teacherRepository.saveAll(List.of(venus, jupyter));

            Course mathVenus = new Course(math, venus);
            Course physicsJupyter = new Course(physics, jupyter);
            Course physicsVenus = new Course(physics, venus);
            courseRepository.saveAll(List.of(mathVenus, physicsJupyter, physicsVenus));

            Lecture a1MathVenus = new Lecture(DayOfWeek.of(1), 7, 9, mathVenus);
            Lecture a2MathVenus = new Lecture(DayOfWeek.of(3), 9, 11, mathVenus);
            Lecture a1physicsJupyter = new Lecture(DayOfWeek.of(5), 14, 17, physicsJupyter);
            lectureRepository.saveAll(List.of(a1MathVenus, a2MathVenus, a1physicsJupyter));

            Grade luneMathVenus = new Grade(5, lune, mathVenus);
            Grade lunePhysicsJupyter = new Grade(2, lune, physicsJupyter);
            Grade soleiMathVenus = new Grade(4, solei, mathVenus);
            Grade soleiPhysicsJupyter = new Grade(5, solei, physicsJupyter);
            gradeRepository.saveAll(List.of(
                    luneMathVenus, lunePhysicsJupyter, soleiMathVenus, soleiPhysicsJupyter));

        };
    }
}
