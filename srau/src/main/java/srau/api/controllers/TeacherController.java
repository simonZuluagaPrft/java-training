package srau.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import srau.api.domain.Teacher;
import srau.api.services.TeacherService;

@RestController
@RequestMapping("api/v1/teacher")
public class TeacherController {
    
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    
    @GetMapping
    public List<Teacher> getTeachers() {
        return teacherService.getTeachers();
    }
}
