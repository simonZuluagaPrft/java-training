package srau.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import srau.api.domain.Student;
import srau.api.repositories.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentByEmail(String studentEmail) {
        Optional<Student> optStudent = studentRepository.findStudentByEmail(studentEmail);

        if (!optStudent.isPresent()) {
            throw new IllegalStateException("No student with email: " + studentEmail);
        }

        return optStudent.get();
    }

}
