package ru.hogwarts.school.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.serviceImpl.FacultyServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {
    @InjectMocks
    private FacultyServiceImpl service;

    @Mock
    private FacultyRepository facultyRepository;

    @Mock
    private StudentRepository studentRepository;

    @Test
    @DisplayName("Добавление факультета")
    void createFaculty() {
        Faculty expected = new Faculty("Gryffindor", "Green");
        when(facultyRepository.save(new Faculty("Gryffindor", "Green"))).thenReturn(expected);

        //test
        Faculty actual = service.createFaculty(expected);

        //check
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    @DisplayName("Нахождение факультета")
    void findFaculty() {
        Faculty expected = new Faculty("Gryffindor", "Green");
        expected.setId(1L);
        when(facultyRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

        //test
        Faculty actual = service.findFaculty(expected.getId());

        //check
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    @DisplayName("Изменение факультета")
    void updateFaculty() {
        Faculty faculty = new Faculty("Gryffindor", "Green");
        faculty.setId(1L);
        when(facultyRepository.existsById(faculty.getId())).thenReturn(true);
        service.createFaculty(faculty);

        Faculty expected = new Faculty("Slytherin", "Green");
        when(facultyRepository.save(expected)).thenReturn(expected);

        //test
        Faculty actual = service.updateFaculty(faculty.getId(), expected);

        //check
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    @DisplayName("Удаление факультета")
    void deleteFaculty() {
        Faculty faculty = new Faculty("Gryffindor", "Green");
        faculty.setId(1L);

        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));

        doNothing().when(facultyRepository).delete(faculty);

        //test
        Faculty actual = service.deleteFaculty(faculty.getId());

        //check
        verify(facultyRepository, times(1)).delete(faculty);
    }

    @Test
    @DisplayName("Получение списка всех факультетов по цвету")
    void getFacultiesByAge() {
        String color = "Red";
        Faculty expected = new Faculty("Gryffindor", "Red");
        Faculty expected2 = new Faculty("Gryffindor1", "Red");

        when(facultyRepository.findAll()).thenReturn(List.of(expected, expected2));

        //test
        List<Faculty> actual = service.getFacultiesByColor(color);

        //check
        assertThat(actual).containsAll(List.of(expected, expected2));
    }

    @Test
    @DisplayName("Поиск факультета по цвету или названию")
    void findFacultyByColorOrName() {
        String name = "Gryffindor";
        String color = "Red";
        Faculty expected = new Faculty(name, color);

        when(facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name)).thenReturn(List.of(expected));

        //test
        List<Faculty> actual = service.findFacultyByColorOrName(color, name);

        //check
        assertThat(actual).containsAll(List.of(expected));
    }

    @Test
    @DisplayName("Поиск всех студентов по id факультета")
    void findStudentsByFaculty() {
        Faculty gryffindor = new Faculty("Gryffindor", "Red");
        gryffindor.setId(1L);
        Student student1 = new Student("Garry", 18);
        student1.setFaculty(gryffindor);
        Student student2 = new Student("Ron", 19);
        student2.setFaculty(gryffindor);

        when(studentRepository.findByFacultyId(gryffindor.getId())).thenReturn(List.of(student1, student2));

        //test
        List<Student> actual = service.findStudentsByFaculty(gryffindor.getId());

        //check
        assertThat(actual).containsAll(List.of(student1, student2));
    }

}