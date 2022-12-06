package srau.api.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import srau.api.domain.Subject;
import srau.api.mapstruct.dto.SubjectGetDto;
import srau.api.mapstruct.mapper.SubjectMapper;
import srau.api.repositories.SubjectRepository;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
    }

    public List<SubjectGetDto> getSubjects() {
        return subjectRepository
                .findAll()
                .stream()
                .map(subjectMapper::subjectToSubjectGetDto)
                .collect(Collectors.toList());
    }

    public Subject getSubjectByName(String subjectName) {
        Optional<Subject> optSubject = subjectRepository.findSubjectByName(subjectName);

        if (optSubject.isEmpty()) {
            throw new IllegalStateException("No subject with name: " + subjectName);
        }

        return optSubject.get();
    }

    public void createSubject(Subject subject) {
        Optional<Subject> subjectOptional = subjectRepository.findSubjectByName(
                subject.getName());

        if (subjectOptional.isPresent()) {
            throw new IllegalStateException("Name taken");
        }
        subjectRepository.save(subject);
    }

    @Transactional
    public void updateSubject(Long subjectId, String name, String description) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalStateException(
                        "subject with id " + subjectId + " does not exists"));

        if (name != null &&
                name.length() > 0 &&
                !Objects.equals(subject.getName(), name)) {

            Optional<Subject> subjectOptional = subjectRepository
                    .findSubjectByName(name);

            if (subjectOptional.isPresent()) {
                throw new IllegalStateException("name taken");
            }

            subject.setName(name);
        }

        if (description != null &&
                description.length() > 0 &&
                !Objects.equals(subject.getDescription(), description)) {
            subject.setDescription(description);
        }

    }

    public void deleteSubject(Long subjectId) {
        boolean exists = subjectRepository.existsById(subjectId);

        if (!exists) {
            throw new IllegalStateException("Subject with id " + subjectId + " does not exists");
        }
        subjectRepository.deleteById(subjectId);
    }

}
