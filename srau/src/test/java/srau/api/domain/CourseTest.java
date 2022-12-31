package srau.api.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseTest {
    @Mock
    Student student;
    private Course underTest;

    @BeforeEach
    void setup() {
        underTest = new Course();
        underTest.setStudents(new HashSet<>());
    }

    @Test
    void addStudent() {
        // when
        underTest.addStudent(student);
        // then
        assertTrue(underTest.getStudents().contains(student));
        assertTrue(student.getCourses().contains(underTest));
    }

    @Test
    void deleteStudent() {
        // given
        underTest.addStudent(student);
        // when
        underTest.deleteStudent(student);
        // then
        assertFalse(underTest.getStudents().contains(student));
        assertFalse(student.getCourses().contains(underTest));
    }
}