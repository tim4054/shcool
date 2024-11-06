package ru.hogwarts.school.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.serviceImpl.StudentServiceImpl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.capitalize;
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
    @DisplayName("Метод поиска студентов по имени и цвету факультета")
    void findStudentByFacultyNameColor() {
        Faculty faculty = new Faculty("Gryffindor", "Red");
        Student expected = new Student("Garry", 18);
        expected.setFaculty(faculty);

        List<Student> students = List.of(expected);

        when(studentRepository.findByFacultyNameAndFacultyColor(faculty.getName(), faculty.getColor()))
                .thenReturn(students);

        //test
        service.findStudentByFacultyNameColor(faculty.getName(), faculty.getColor());

        //check
        assertThat(students).hasSize(1);
        assertThat(students.get(0)).isEqualTo(expected);

        verify(studentRepository, only()).findByFacultyNameAndFacultyColor(faculty.getName(), faculty.getColor());
    }

    @Test
    @DisplayName("Метод поиска студентов по имени и цвету факультета, когда лист пуст")
    void findStudentByFacultyNameColor_Negative() {
        Faculty faculty = new Faculty("Gryffindor", "Red");
        List<Student> students = new ArrayList<>();


        when(studentRepository.findByFacultyNameAndFacultyColor(faculty.getName(), faculty.getColor()))
                .thenReturn(Collections.emptyList());

        //test
        service.findStudentByFacultyNameColor(faculty.getName(), faculty.getColor());

        //check
        assertThat(students).isEmpty();

        verify(studentRepository, only()).findByFacultyNameAndFacultyColor(faculty.getName(), faculty.getColor());
    }

    @ParameterizedTest
    @DisplayName("Выводит список имен на А, если таковые имеются, регистр игнорирутеся")
    @MethodSource("getNames")
    void getNamesStartWithA(String name1, String name2) {
        Student expected1 = new Student(name1, 18);
        Student expected2 = new Student(name2, 18);
        List<Student> students = List.of(
                new Student("Garry", 18),
                new Student("Ron", 19),
                expected1,
                expected2);

        when(studentRepository.findAll()).thenReturn(students);

        //test
        List<String> actual = service.getNamesStartWithA();

        //check
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0)).isEqualTo(expected1.getName());
        assertThat(actual.get(0)).isEqualTo(capitalize(expected2.getName()));

        verify(studentRepository, only()).findAll();
    }

    @Test
    @DisplayName("Выводит пустой список, если нет имен на А")
    void getNamesStartWithA_Negative() {
        when(studentRepository.findAll()).thenReturn(Collections.emptyList());

        //test
        List<String> actual = service.getNamesStartWithA();

        //check
        assertThat(actual).isEmpty();
        verify(studentRepository, only()).findAll();
    }


    @Test
    @DisplayName("Отображение среднего возраста через стрим")
    void getStudentsAverageAgeByStream() {
        List<Student> students = List.of(
                new Student("Garry", 18),
                new Student("Ron", 19),
                new Student("Александр", 18));
        double expected = students.stream()
                .mapToDouble(Student::getAge)
                .reduce(0, Double::sum) / students.size();

        when(studentRepository.findAll()).thenReturn(students);

        //test
        double actual = service.getStudentsAverageAgeByStream();

        //check
        assertThat(actual).isEqualTo(expected);

        verify(studentRepository, only()).findAll();
    }

    @Test
    @DisplayName("Отображение среднего возраста через стрим, когда список пустой")
    void getStudentsAverageAgeByStream_WhenStudentListIsEmpty() {

        when(studentRepository.findAll()).thenReturn(Collections.emptyList());

        //test
        double actual = service.getStudentsAverageAgeByStream();

        //check
        assertThat(actual).isZero();

        verify(studentRepository, only()).findAll();
    }

    public static Stream<Arguments> getNames() {
        return Stream.of(
                Arguments.of("Александр", "александр")
        );
    }

}
