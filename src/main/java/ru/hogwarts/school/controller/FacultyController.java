package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
@Tag(name = "Контроллер факультетов")
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @PostMapping("/add")
    @Operation(summary = "Добавление факультета",
            description = "Id проставляется из репозитория")
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createdFaculty = service.createFaculty(faculty);
        return ResponseEntity.ok(createdFaculty);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск факультета",
            description = "Поиск по Id")
    public ResponseEntity<Faculty> findFacultyById(@PathVariable long id) {
        Faculty findFacultyById = service.findFaculty(id);
        if (findFacultyById == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findFacultyById);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование факультета",
            description = "Редактирование по Id")
    public Faculty updateFaculty(@PathVariable long id,
                                 @RequestBody Faculty faculty) {
        return service.updateFaculty(id, faculty);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление факультета",
            description = "Удаление по Id")
    public Faculty deleteFaculty(@PathVariable long id) {
        return service.deleteFaculty(id);
    }

    @GetMapping("/get-by-color/{color}")
    @Operation(summary = "Поиск факультета по цвету")
    public ResponseEntity<List<Faculty>> getFacultiesByColor(@PathVariable String color) {
        List<Faculty> findFaculties = service.getFacultiesByColor(color);
        if (findFaculties == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findFaculties);
    }

    @GetMapping("/get/by-color-or-name")
    @Operation(summary = "Поиск факультета по названию или по цвету ",
            description = "Показывает факультет, соответствующий цвету или названию запроса")
    public List<Faculty> findFacultyByColorOrName(@RequestParam(name = "Цвет", required = false) String color,
                                                  @RequestParam(name = "Название", required = false) String name) {
        return service.findFacultyByColorOrName(color, name);
    }

    @GetMapping("/{id}/get-students-by-faculty")
    @Operation(summary = "Поиск всех студентов факультета",
            description = "Поиск по id факультета")
    public List<Student> studentsByFaculty(@PathVariable long id) {
        return service.findStudentsByFaculty(id);
    }
}
