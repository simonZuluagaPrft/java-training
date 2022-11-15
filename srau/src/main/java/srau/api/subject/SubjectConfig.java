package srau.api.subject;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
