package srau.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import srau.api.domain.Subject;
import srau.api.exception.ElementNotFoundException;
import srau.api.exception.ElementTakenException;
import srau.api.mapstruct.dto.SubjectGetDto;
import srau.api.mapstruct.mapper.SubjectMapper;
import srau.api.repositories.SubjectRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Subject getSubjectByName(String subjectName) throws ElementNotFoundException {
        return subjectRepository.findSubjectByName(subjectName)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No subject with name: " + subjectName));
    }

    public void createSubject(Subject subject) throws ElementTakenException {
        Optional<Subject> subjectOptional = subjectRepository.findSubjectByName(subject.getName());

        if (subjectOptional.isPresent()) {
            throw new ElementTakenException("Name taken");
        }
        subjectRepository.save(subject);
    }

    @Transactional
    public SubjectGetDto updateSubject(Long subjectId, String name, String description)
            throws ElementNotFoundException, ElementTakenException {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No subject with id: " + subjectId));

        if (name != null &&
                name.length() > 0 &&
                !Objects.equals(subject.getName(), name)) {

            Optional<Subject> subjectOptional = subjectRepository
                    .findSubjectByName(name);

            if (subjectOptional.isPresent()) {
                throw new ElementTakenException("Name taken");
            }

            subject.setName(name);
        }

        if (description != null &&
                description.length() > 0 &&
                !Objects.equals(subject.getDescription(), description)) {
            subject.setDescription(description);
        }

        return subjectMapper.subjectToSubjectGetDto(subject);
    }

    public void deleteSubject(Long subjectId) throws ElementNotFoundException {
        boolean exists = subjectRepository.existsById(subjectId);

        if (!exists) {
            throw new ElementNotFoundException("No subject with id: " + subjectId);
        }
        subjectRepository.deleteById(subjectId);
    }
}
