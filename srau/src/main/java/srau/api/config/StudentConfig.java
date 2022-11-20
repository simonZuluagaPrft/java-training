package srau.api.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import srau.api.domain.Student;
import srau.api.repositories.StudentRepository;

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
