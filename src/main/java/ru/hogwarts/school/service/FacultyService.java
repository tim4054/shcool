package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class FacultyService {
    private Long id = 0L;
    private final Map<Long, Faculty> faculties = new HashMap<>();

    public Faculty createFaculty(Faculty faculty) {
        faculties.put(id++, faculty);
        return faculty;
    }

    public Faculty getFacultyById(Long id) {
        return faculties.get(id);
    }

    public Faculty updateFaculty(Long id, Faculty faculty) {
        faculties.put(id, faculty);
        return faculty;
    }

    public Faculty deleteStudent(Long id) {
        return faculties.remove(id);
    }

    public List<Faculty> getFacultiesByColor(String color) {
        List<Faculty> list = faculties.values()
                .stream()
                .filter(x -> x.getColor().equals(color))
                .toList();
        return Collections.unmodifiableList(list);
    }
}
