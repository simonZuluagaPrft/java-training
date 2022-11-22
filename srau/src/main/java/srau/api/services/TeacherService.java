package srau.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import srau.api.domain.Teacher;
import srau.api.repositories.TeacherRepository;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacherByEmail(String teacherEmail) {
        Optional<Teacher> optTeacher = teacherRepository.findTeacherByEmail(teacherEmail);

        if (!optTeacher.isPresent()) {
            throw new IllegalStateException("No teacher with email: " + teacherEmail);
        }

        return optTeacher.get();
    }

    public void createTeacher(Teacher teacher) {
        Optional<Teacher> teacherOptional = teacherRepository.findTeacherByEmail(
                teacher.getEmail());

        if (teacherOptional.isPresent()) {
            throw new IllegalStateException("Email taken");
        }
        teacherRepository.save(teacher);
    }

}
