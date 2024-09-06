package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private Long id = 0L;

    private final Map<Long, Student> students = new HashMap<>();

    public Student createStudent(Student student) {
        students.put(id++, student);
        return student;
    }

    public Student getStudentById(Long id) {
        return students.get(id);
    }

    public Student updateStudent(Long id, Student student) {
        students.put(id, student);
        return student;
    }

    public Student deleteStudent(Long id) {
        return students.remove(id);
    }

    public List<Student> getStudentsByAge(Long age) {
        List<Student> list = students.values()
                .stream()
                .filter(x -> x.getAge() == age)
                .toList();
        return Collections.unmodifiableList(list);
    }
}
