package ru.hogwarts.school.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

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
}