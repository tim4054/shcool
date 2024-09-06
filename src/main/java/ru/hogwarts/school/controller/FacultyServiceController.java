package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyServiceController {
    private final FacultyService service;

    public FacultyServiceController(FacultyService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createdFaculty = service.createFaculty(faculty);
        return ResponseEntity.ok(createdFaculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty findfaculty = service.getFacultyById(id);
        if (findfaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findfaculty);
    }

    @PutMapping()
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updatedfaculty = service.updateFaculty(faculty.getId(), faculty);
        if (updatedfaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedfaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        Faculty deletedfaculty = service.deleteStudent(id);
        if (deletedfaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deletedfaculty);
    }

    @GetMapping("{age}")
    public ResponseEntity<List<Faculty>> getFacultiesByColor(@PathVariable String color) {
        List<Faculty> findFaculties = service.getFacultiesByColor(color);
        if (findFaculties == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findFaculties);
    }
}
