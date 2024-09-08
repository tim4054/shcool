package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class FacultyServiceImpl implements FacultyService {
    private static long id = 1;
    private final Map<Long, Faculty> faculties = new HashMap<>();

    @Override
    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(id++);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty findFaculty(long id) {
        return faculties.get(id);
    }

    @Override
    public Faculty updateFaculty(long id, Faculty faculty) {
        faculty.setId(id);
        faculties.put(id, faculty);
        return faculty;
    }

    @Override
    public Faculty deleteStudent(long id) {
        return faculties.remove(id);
    }

    @Override
    public List<Faculty> getFacultiesByColor(String color) {
        return faculties.values()
                .stream()
                .filter(x -> x.getColor().equals(color))
                .toList();
    }
}
