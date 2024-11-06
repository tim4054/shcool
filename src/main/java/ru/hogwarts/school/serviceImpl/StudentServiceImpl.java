package ru.hogwarts.school.serviceImpl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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

    public final Object flag = new Object();

    @Override
    public Student createStudent(Student student) {
        Student savedStudent = studentRepository.save(student);
        logger.info("Добавляется студент с id:{}", savedStudent.getId());
        return savedStudent;
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
        logger.info("Вызван метод поиска студента по имени");
        return studentRepository.getStudentByName(name);
    }

    @Override
    public List<Student> findStudentByFacultyNameColor(String name, String color) {
        logger.info("Вызван метод поиска студентов по имени и цвету факультета");
        return studentRepository.findByFacultyNameAndFacultyColor(name, color);
    }

    @Override
    public List<String> getNamesStartWithA() {
        logger.info("Вызван метод отображения списка имен на А через стрим");
        return studentRepository.findAll().stream()
                .parallel()
                .map(Student::getName)
                .map((StringUtils::capitalize))
                .filter(s -> s.startsWith("А"))
                .sorted()
                .toList();
    }

    @Override
    public double getStudentsAverageAgeByStream() {
        logger.info("Вызван метод отображения среднего возраста через стрим");
        return studentRepository.findAll().stream()
                .parallel()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }

    @Override
    public void getStudentNamesParallel() {
        System.out.println(studentRepository.findById(1L).orElseThrow().getName());
        System.out.println(studentRepository.findById(2L).orElseThrow().getName());

        new Thread(() -> {
            System.out.println(studentRepository.findById(3L).orElseThrow().getName());
            System.out.println(studentRepository.findById(4L).orElseThrow().getName());
        }).start();

        new Thread(() -> {
            System.out.println(studentRepository.findById(5L).orElseThrow().getName());
            System.out.println(studentRepository.findById(6L).orElseThrow().getName());
        }).start();
    }

    @Override
    public void getStudentNamesSynchronized() {
        printNamesSynchronized(1L);
        printNamesSynchronized(2L);

        new Thread(() -> {
            printNamesSynchronized(3L);
            printNamesSynchronized(4L);
        }).start();

        new Thread(() -> {
            printNamesSynchronized(5L);
            printNamesSynchronized(6L);
        }).start();
    }

    public void printNamesSynchronized(long id) {
        synchronized (flag) {
            System.out.println(studentRepository.findById(id).orElseThrow().getName());
        }
    }
}


