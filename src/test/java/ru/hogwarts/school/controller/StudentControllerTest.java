package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.serviceImpl.StudentServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @SpyBean
    private StudentServiceImpl studentService;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createStudent() throws Exception {
        String name = "Garry";
        int age = 18;
        Faculty faculty = new Faculty("Griffyndor", "Red");
        Student expected = new Student(name, age);
        expected.setFaculty(faculty);
        when(studentRepository.save(expected)).thenReturn(expected);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/student/add")
                .content(studentObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.faculty.name").value("Griffyndor"));
    }

    @Test
    void findStudentById() {
    }

    @Test
    void updateStudent() {
    }

    @Test
    void deleteStudent() {
    }

    @Test
    @DisplayName("Возвращает всех студентов по возрасту")
    void getAllStudentsByAge() throws Exception {
        String name1 = "Garry";
        String name2 = "Ron";
        int age1 = 18;
        int age2 = 19;
        Student student1 = new Student(name1, age1);
        student1.setId(1L);
        Student student2 = new Student(name2, age2);

        List<Student> expectedList = List.of(student1, student2);

        when(studentRepository.findAll()).
                thenReturn(List.of(student1, student2));


        mockMvc.perform(MockMvcRequestBuilders.get("/student/get-by-age/" + age1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value(name1))
                .andExpect(jsonPath("$[0].age").value(age2));


        //System.out.println(expectedList);
        //System.out.println(actualList);
    }

    @Test
    void findByAgeBetween() {
    }

    @Test
    void getFaculty() {
    }
}