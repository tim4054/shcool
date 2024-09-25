package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.serviceImpl.StudentServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTestRestTemplate {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentServiceImpl;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void createStudent() throws Exception{
        Student expected = new Student("Garry", 18);
        expected.setId(10L);
        when(studentRepository.save(expected)).thenReturn(expected);

        //test
        Student actual = (this.restTemplate.postForObject("http://localhost:" + port + "/add", expected, Student.class));

        /*
        check
        Assertions.assertThat(expected).isEqualTo(actual);
        assertEquals(expected, actual);
        System.out.println(expected);
        */
        System.out.println(actual);
    }

    @Test
    void findStudentById() {
        Student expected = new Student("Garry", 18);
        expected.setId(10L);
        when(studentRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

        //test
        Student actual = (this.restTemplate.getForObject("http://localhost:" + port + "/find", Student.class));

        //check
        System.out.println(actual);

    }

    @Test
    void updateStudent() {
    }

    @Test
    void deleteStudent() {
    }

    @Test
    void getAllStudentsByAge() {
    }

    @Test
    void findByAgeBetween() {
    }

    @Test
    void getFaculty() {
    }
}