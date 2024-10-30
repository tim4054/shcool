package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty findFaculty(long id);

    Faculty updateFaculty(long id, Faculty faculty);

    Faculty deleteFaculty(long id);

    List<Faculty> getFacultiesByColor(String color);

    List<Faculty> findFacultyByColorOrName(String color, String name);

    List<Student> findStudentsByFaculty(long id);
}

