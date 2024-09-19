package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exception.FacultyNotFoundException;
import ru.hogwarts.school.Exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(long id) {
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public Student updateStudent(long id, Student forUpdateStudent) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        forUpdateStudent.setId(id);
        return studentRepository.save(forUpdateStudent);
    }

    @Override
    public Student deleteStudent(long id) {
        Student studentForDelete = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        studentRepository.delete(studentForDelete);
        return studentForDelete;
    }

    @Override
    public List<Student> getStudentsByAge(int age) {
        return studentRepository.findAll()
                .stream()
                .filter(x -> x.getAge() == age)
                .toList();
    }
}
