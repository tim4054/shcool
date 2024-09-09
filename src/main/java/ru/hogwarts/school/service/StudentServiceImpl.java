package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    private static long id = 1;

    private final Map<Long, Student> students = new HashMap<>();

    @Override
    public Student createStudent(Student student) {
        student.setId(id++);
        students.put(student.getId(), student);
        return student;
    }

    @Override
    public Student findStudent(long id) {
        return students.get(id);
    }

    @Override
    public Student updateStudent(long id, Student student) {
        student.setId(id);
        students.put(id, student);
        return student;
    }

    @Override
    public Student deleteStudent(long id) {
        return students.remove(id);
    }

    @Override
    public List<Student> getStudentsByAge(int age) {
        return students.values()
                .stream()
                .filter(x -> x.getAge() == age)
                .toList();
    }
}
