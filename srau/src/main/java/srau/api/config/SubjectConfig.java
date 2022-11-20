package srau.api.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import srau.api.domain.Subject;
import srau.api.repositories.SubjectRepository;

@Configuration
public class SubjectConfig {

    @Bean
    CommandLineRunner subjectCommandLineRunner(SubjectRepository repository) {
        return args -> {
            Subject math = new Subject("Math", "Some numbers");
            Subject language = new Subject("Language", "Some words");

            repository.saveAll(List.of(math, language));
        };
    }
}
