package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
@Tag(name = "Контроллер факультетов")
public class FacultyServiceController {
    private final FacultyService service;

    public FacultyServiceController(FacultyService service) {
        this.service = service;
    }

    @PostMapping("/add")
    @Operation(summary = "Добавление факультета",
            description = "Id проставляется из счетчика")
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
    public ResponseEntity<Faculty> updateFaculty(@PathVariable long id,
                                                 @RequestBody Faculty faculty) {
        Faculty updatedfaculty = service.updateFaculty(id, faculty);
        if (updatedfaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedfaculty);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление факультета",
            description = "Удаление по Id")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable long id) {
        Faculty deletedfaculty = service.deleteStudent(id);
        if (deletedfaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deletedfaculty);
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
}
