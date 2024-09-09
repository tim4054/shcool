package ru.hogwarts.school.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class StudentServiceImplTest {
    private final StudentServiceImpl service = new StudentServiceImpl();

    @Test
    @DisplayName("Добавление студента")
    void createStudent() {
        Student expected = new Student("Garry", 18);

        //test
        Student actual = service.createStudent(expected);

        //check
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    @DisplayName("Нахождение студента")
    void findStudent() {
        Student student = new Student("Garry", 18);
        Student expected = service.createStudent(student);

        //test
        Student actual = service.findStudent(expected.getId());

        //check
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    @DisplayName("Изменение студента")
    void updateStudent() {
        Student student = new Student("Garry", 18);
        Student savedStudent = service.createStudent(student);
        Student expected = new Student("Ron", 19);

        //test
        Student actual = service.updateStudent(savedStudent.getId(), expected);

        //check
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    @DisplayName("Удаление студента")
    void deleteStudent() {
        Student student = new Student("Garry", 18);
        Student expected = service.createStudent(student);

        //test
        Student actual = service.deleteStudent(expected.getId());

        //check
        assertThat(expected).isEqualTo(actual);
        assertThat(service.findStudent(expected.getId())).isNull();
    }

    @Test
    @DisplayName("Получение списка всех студентов по возрасту")
    void getStudentsByAge() {
        int age = 18;
        Student expected = new Student("Garry", 18);
        Student expected2 = new Student("Germiona", 18);
        service.createStudent(expected);
        service.createStudent(expected2);

        //test
        List<Student> actual = service.getStudentsByAge(age);

        //check
        assertThat(actual).containsAll(List.of(expected, expected2));
    }
}