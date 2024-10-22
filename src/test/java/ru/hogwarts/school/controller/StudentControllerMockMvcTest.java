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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.serviceImpl.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerMockMvcTest {

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
        faculty.setId(1L);
        Student expected = new Student(name, age);
        expected.setId(1L);
        expected.setFaculty(faculty);
        when(studentRepository.save(expected)).thenReturn(expected);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        studentObject.put("faculty", faculty);

        mockMvc.perform(MockMvcRequestBuilders.post("/student/add")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

    }

    @Test
    void findStudentById() throws Exception {
        String name = "Garry";
        int age = 18;
        long id = 1L;
        Student expected = new Student(name, age);
        expected.setId(id);
        when(studentRepository.findById(id)).thenReturn(Optional.of(expected));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/find/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void updateStudent() throws Exception {
        String name1 = "Garry";
        String name2 = "Ron";
        int age1 = 18;
        int age2 = 19;
        Student student1 = new Student(name1, age1);
        student1.setId(1L);
        Student student2 = new Student(name2, age2);

        when(studentRepository.existsById(1L)).thenReturn(true);
        when(studentService.updateStudent(1L, student2)).thenReturn(student2);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name2);
        studentObject.put("age", age2);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student/update/1")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name").value(name2))
                .andExpect(jsonPath("$.age").value(age2));
    }

    @Test
    void deleteStudent() throws Exception {
        String name = "Garry";
        int age = 18;
        long id = 1L;
        Student expected = new Student(name, age);
        expected.setId(id);

        when(studentRepository.findById(id)).thenReturn(Optional.of(expected));
        when(studentService.deleteStudent(id)).thenReturn(expected);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/delete/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
        ;
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

        when(studentRepository.findAll()).
                thenReturn(List.of(student1, student2));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/get-by-age/" + age1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value(name1))
                .andExpect(jsonPath("$[0].age").value(age1));
    }

    @Test
    void findByAgeBetween() throws Exception {
        String name1 = "Garry";
        String name2 = "Ron";
        int age1 = 18;
        int age2 = 19;
        int minAge = 17;
        int maxAge = 20;
        Student student1 = new Student(name1, age1);
        Student student2 = new Student(name2, age2);

        when(studentService.findByAgeBetween(minAge, maxAge)).
                thenReturn(List.of(student1, student2));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/get-by-age-between")
                        .param("Минимальный возраст", String.valueOf(minAge))
                        .param("Максимальный возраст", String.valueOf(maxAge)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value(name1))
                .andExpect(jsonPath("$[0].age").value(age1))
                .andExpect(jsonPath("$[1].name").value(name2))
                .andExpect(jsonPath("$[1].age").value(age2));
    }

    @Test
    void getFaculty() throws Exception {
        String name = "Garry";
        int age = 18;
        long id = 1L;
        Faculty faculty = new Faculty("Griffyndor", "Red");
        Student expected = new Student(name, age);
        expected.setFaculty(faculty);
        expected.setId(id);

        JSONObject facultyJSON = new JSONObject();
        facultyJSON.put("name", "Griffyndor");
        facultyJSON.put("color", "Red");

        when(studentRepository.findById(id)).thenReturn(Optional.of(expected));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/1/get-faculty", 1L))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("Griffyndor"))
                .andExpect(jsonPath("$.color").value("Red"));
    }

}