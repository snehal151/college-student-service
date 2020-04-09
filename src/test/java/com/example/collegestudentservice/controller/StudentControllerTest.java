package com.example.collegestudentservice.controller;

import com.example.collegestudentservice.model.Student;
import com.example.collegestudentservice.repository.StudentRepository;
import com.example.collegestudentservice.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class StudentControllerTest {

    @MockBean
    StudentRepository studentRepository;

    @MockBean
    StudentService studentService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void happyPathValidPost() throws Exception {


        mvc.perform(post("/student")
                .content("{\n" +
                        "\t\"firstName\": \"testFirstName\",\n" +
                        "\t\"lastName\": \"testLastName\",\n" +
                        "\t\"email\": \"testEmail@gmail.com\",\n" +
                        "\t\"marks\": \"88\",\n" +
                        "\t\"collegeName\": \"testCollegeName\",\n" +
                        "\t\"address\": \"testAddress\",\n" +
                        "\t\"grade\": \"A\"\n" +

                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
}

    @Test
    public void errorPathNoBodyWhilePosting() throws Exception {

        mvc.perform(post("/student"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void happyPathValidPostAndGet() throws Exception {

        Student dummyResponse = new Student();
        dummyResponse.setId(1);
        Mockito.when(studentService.findById(1)).thenReturn(new ResponseEntity<>(dummyResponse, HttpStatus.OK));

        mvc.perform(get("/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void errorPathGetStudentWithoutPost() throws Exception{
        Mockito.when(studentService.findById(102)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mvc.perform(get("/student/102"))
                .andExpect(status().isNotFound());
    }
    @Test
    public void happyPathGetStudentWithValidPost() throws Exception{
        Student  dummyResponse = new Student();
        dummyResponse.setId(1);

        Mockito.when(studentService.findById(1)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        mvc.perform(get("/student/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void deleteApplication() throws Exception{
        this.mvc.perform(MockMvcRequestBuilders
                        .delete("/student/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

}
