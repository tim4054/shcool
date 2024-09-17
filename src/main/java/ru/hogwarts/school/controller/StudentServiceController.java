package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
@Tag(name = "Контроллер студентов")
public class StudentServiceController {
    private final StudentService service;

    public StudentServiceController(StudentService service) {
        this.service = service;
    }

    @PostMapping("/add")
    @Operation(summary = "Добавление студента",
            description = "Id проставляется из репозитория")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = service.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск студента",
            description = "Поиск по Id")
    public ResponseEntity<Student> findStudentById(@PathVariable Long id) {
        Student findStudentById = service.findStudent(id);
        if (findStudentById == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findStudentById);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование студента",
            description = "Редактирование по Id")
    public Student updateStudent(@PathVariable long id,
                                 @RequestBody Student studentForUpdate) {
        return service.updateStudent(id, studentForUpdate);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление студента",
            description = "Удаление по Id")
    public Student deleteStudent(@PathVariable long id) {
        return service.deleteStudent(id);
    }

    @GetMapping("/get-by-age/{age}")
    @Operation(summary = "Поиск студентов по возрасту",
            description = "Показывает всех студентов, соответсвующих возрасту запроса")
    public ResponseEntity<List<Student>> getAllStudentsByAge(@PathVariable int age) {
        List<Student> findStudents = service.getStudentsByAge(age);
        if (findStudents == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findStudents);
    }
}
