package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.serviceImpl.StudentServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTestRestTemplate {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void createStudent() throws Exception {
        String name = "Garry";
        int age = 18;
        Student expected = new Student(name, age);

        //test
        ResponseEntity<Student> actual = (this.restTemplate.postForEntity("http://localhost:" + port + "/student/add", expected, Student.class));

        //check
        assertTrue(actual.getStatusCode().is2xxSuccessful());
        assertEquals(expected.getName(), actual.getBody().getName());
        assertEquals(expected.getAge(), actual.getBody().getAge());
    }

    @Test
    void findStudentById() {
        String name = "Garry";
        int age = 18;
        Student createdStudent = new Student(name, age);
        Student expected = this.restTemplate.postForObject("http://localhost:" + port + "/student/add", createdStudent, Student.class);

        //test
        Student actual = (this.restTemplate.getForObject("http://localhost:" + port + "/student/find/" + expected.getId(), Student.class));

        //check
        Assertions.assertThat(expected).isEqualTo(actual);
    }

    @Test
    void updateStudent() {
        String name1 = "Garry";
        int age1 = 18;
        Student createdStudent1 = new Student(name1, age1);
        Student expected1 = this.restTemplate.postForObject("http://localhost:" + port + "/student/add", createdStudent1, Student.class);

        String name2 = "Ron";
        int age2 = 19;
        Student createdStudent2 = new Student(name2, age2);

        this.restTemplate.put("http://localhost:" + port + "/student/update/" + expected1.getId(), createdStudent2);
        Student actual = this.restTemplate.getForObject("http://localhost:" + port + "/student/find/" + expected1.getId(), Student.class);

        //check
        Assertions.assertThat(createdStudent2.getName()).isEqualTo(actual.getName());
        Assertions.assertThat(createdStudent2.getAge()).isEqualTo(actual.getAge());
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