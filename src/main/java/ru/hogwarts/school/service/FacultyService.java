package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty findFaculty(long id);

    Faculty updateFaculty(long id, Faculty faculty);

    Faculty deleteStudent(long id);

    List<Faculty> getFacultiesByColor(String color);
}
