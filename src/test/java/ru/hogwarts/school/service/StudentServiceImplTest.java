package ru.hogwarts.school.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.serviceImpl.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @InjectMocks
    private StudentServiceImpl service;

    @Mock
    private StudentRepository studentRepository;

    @Test
    @DisplayName("Добавление студента")
    void createStudent() {
        Student expected = new Student("Garry", 18);
        when(studentRepository.save(new Student("Garry", 18))).thenReturn(expected);

        //test
        Student actual = service.createStudent(expected);

        //check
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    @DisplayName("Нахождение студента")
    void findStudent() {
        Student expected = new Student("Garry", 18);
        expected.setId(1L);
        when(studentRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

        //test
        Student actual = service.findStudent(expected.getId());

        //check
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    @DisplayName("Изменение студента")
    void updateStudent() {
        Student student = new Student("Garry", 18);
        student.setId(1L);
        when(studentRepository.existsById(student.getId())).thenReturn(true);
        service.createStudent(student);

        Student expected = new Student("Ron", 19);
        when(studentRepository.save(expected)).thenReturn(expected);

        //test
        Student actual = service.updateStudent(student.getId(), expected);

        //check
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    @DisplayName("Удаление студента")
    void deleteStudent() {
        Student student = new Student("Garry", 18);
        student.setId(1L);

        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        doNothing().when(studentRepository).delete(student);

        //test
        Student actual = service.deleteStudent(student.getId());

        //check
        verify(studentRepository, times(1)).delete(student);
    }

    @Test
    @DisplayName("Получение списка всех студентов по возрасту")
    void getStudentsByAge() {
        int age = 18;
        Student expected = new Student("Garry", 18);
        Student expected2 = new Student("Germiona", 18);

        when(studentRepository.findAll()).thenReturn(List.of(expected, expected2));

        //test
        List<Student> actual = service.getStudentsByAge(age);

        //check
        assertThat(actual).containsAll(List.of(expected, expected2));
    }



}
