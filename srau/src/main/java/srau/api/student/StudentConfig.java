package srau.api.student;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {
    
    @Bean
    CommandLineRunner studentCommandLineRunner(StudentRepository repository) {
        return args -> {
            Student lune = new Student("Lune", "lune@gmail.com");
            Student solei = new Student("Solei", "solei@gmail.com");

            repository.saveAll(List.of(lune, solei));
        };
    }
}
