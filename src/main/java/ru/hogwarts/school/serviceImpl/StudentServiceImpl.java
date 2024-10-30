package ru.hogwarts.school.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        Student savedStudent = studentRepository.save(student);
        logger.info("Добавляется студент с id:{}", savedStudent.getId());
        return studentRepository.findById(savedStudent.getId()).get();
    }

    @Override
    public Student findStudent(long id) {
        logger.info("Поиск студента c id:{}", id);
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public Student updateStudent(long id, Student forUpdateStudent) {
        if (!studentRepository.existsById(id)) {
            logger.error("Нет студента с id = " + id);
            throw new StudentNotFoundException(id);
        }
        forUpdateStudent.setId(id);
        logger.info("Редактирование студента c id:{}", id);
        return studentRepository.save(forUpdateStudent);
    }

    @Override
    public Student deleteStudent(long id) {
        try {
            Student studentForDelete = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
            studentRepository.delete(studentForDelete);
            logger.info("Удаление студента c id:{}", id);
            return studentForDelete;
        } catch (StudentNotFoundException e) {
            logger.error("Студент с id:{} не найден", id, e);
            throw e;
        }
    }



    @Override
    public List<Student> findByAgeBetween(int minAge, int maxAge) {
        logger.info("Отображение списка студентов в возрасте от " + minAge + " до " + maxAge);
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Faculty getFaculty(long id) {
        logger.info("Отображение факультета студента c id:{}", id);
        return studentRepository.findById(id).orElseThrow().getFaculty();
    }

    @Override
    public int getStudentsAmount() {
        logger.info("Вызван метод отображения числа студентов");
        return studentRepository.getStudentsAmount();
    }

    @Override
    public float getStudentsAverageAge() {
        logger.info("Вызван метод отображения среднего возраста студентов");
        return studentRepository.getStudentsAverageAge();
    }
    @Override
    public List<Student> getFiveLastStudents() {
        logger.info("Вызван метод отображения пяти последних студентов");
        return studentRepository.getFiveLastStudents();
    }

    @Override
    public Student findStudentByName(String name) {
        logger.info("Вызван метод отображения поска студента по имени");
        return studentRepository.getStudentByName(name);
    }

    @Override
    public List<Student> findStudentByFacultyNameColor(String name, String color) {
        logger.info("Вызван метод отображения поска студента по имени и цвету факультета");
        return studentRepository.findByFacultyNameAndFacultyColor(name, color);
    }
}

