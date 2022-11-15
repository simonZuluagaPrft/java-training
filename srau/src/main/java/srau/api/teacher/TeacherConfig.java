package srau.api.teacher;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeacherConfig {
    
    @Bean
    CommandLineRunner teacheCommandLineRunner(TeacherRepository repository) {
        return args -> {
            Teacher venus = new Teacher("Venus", "venus@gmail.com");
            Teacher pluto = new Teacher("Pluto", "pluto@gmail.com");

            repository.saveAll(List.of(venus, pluto));
        };
    }
}
