package srau.api.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

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

    @Transactional
    public void updateTeacher(Long teacherId, String name, String email) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalStateException(
                        "teacher with id " + teacherId + " does not exists"));

        if (name != null &&
                name.length() > 0 &&
                !Objects.equals(teacher.getName(), name)) {
            teacher.setName(name);
        }

        if (email != null &&
                email.length() > 0 &&
                !Objects.equals(teacher.getEmail(), email)) {

            Optional<Teacher> teacherOptional = teacherRepository
                    .findTeacherByEmail(email);

            if (teacherOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            }

            teacher.setEmail(email);
        }
    }

    public void deleteTeacher(Long teacherId) {
        boolean exists = teacherRepository.existsById(teacherId);

        if (!exists) {
            throw new IllegalStateException("Teacher with id " + teacherId + " does not exists");
        }
        teacherRepository.deleteById(teacherId);
    }

}
