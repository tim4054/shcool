package ru.hogwarts.school.serviceImpl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id));
    }

    @Override
    public Faculty updateFaculty(long id, Faculty facultyForUpdate) {
        if (!facultyRepository.existsById(id)) {
            throw new FacultyNotFoundException(id);
        }
        facultyForUpdate.setId(id);
        return facultyRepository.save(facultyForUpdate);
    }

    @Override
    public Faculty deleteFaculty(long id) {
        Faculty facultyForDelete = facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id));
        facultyRepository.delete(facultyForDelete);
        return facultyForDelete;
    }

    @Override
    public List<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findAll()
                .stream()
                .filter(x -> x.getColor().equals(color))
                .toList();
    }

    @Override
    public List<Faculty> findFacultyByColorOrName(String color, String name) {
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

    @Override
    public List<Student> findStudentsByFaculty(long id) {
        return studentRepository.findByFacultyId(id);
    }

}

