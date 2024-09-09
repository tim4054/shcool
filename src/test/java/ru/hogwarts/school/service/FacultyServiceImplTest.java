package ru.hogwarts.school.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FacultyServiceImplTest {
    private final FacultyServiceImpl service = new FacultyServiceImpl();

    @Test
    @DisplayName("Добавление факультета")
    void createFaculty() {
        Faculty expected = new Faculty("Gryffindor", "Red");

        //test
        Faculty actual = service.createFaculty(expected);

        //check
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    @DisplayName("Нахождение факультета")
    void findFaculty() {
        Faculty faculty = new Faculty("Gryffindor", "Red");
        Faculty expected = service.createFaculty(faculty);

        //test
        Faculty actual = service.findFaculty(expected.getId());

        //check
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    @DisplayName("Изменение факультета")
    void updateFaculty() {
        Faculty Faculty = new Faculty("Gryffindor", "Red");
        Faculty savedFaculty = service.createFaculty(Faculty);
        Faculty expected = new Faculty("Slytherin", "Green");

        //test
        Faculty actual = service.updateFaculty(savedFaculty.getId(), expected);

        //check
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    @DisplayName("Удаление факультета")
    void deleteFaculty() {
        Faculty faculty = new Faculty("Gryffindor", "Red");
        Faculty expected = service.createFaculty(faculty);

        //test
        Faculty actual = service.deleteFaculty(expected.getId());

        //check
        assertThat(expected).isEqualTo(actual);
        assertThat(service.findFaculty(expected.getId())).isNull();
    }

    @Test
    @DisplayName("Получение списка всех факультетов по цвету")
    void getFacultiesByAge() {
        String color = "Red";
        Faculty expected = new Faculty("Gryffindor", "Red");
        Faculty expected2 = new Faculty("Gryffindor1", "Red");
        service.createFaculty(expected);
        service.createFaculty(expected2);

        //test
        List<Faculty> actual = service.getFacultiesByColor(color);

        //check
        assertThat(actual).containsAll(List.of(expected, expected2));
    }
}