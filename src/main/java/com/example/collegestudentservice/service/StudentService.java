package com.example.collegestudentservice.service;

import com.example.collegestudentservice.model.College;
import com.example.collegestudentservice.model.Student;
import com.example.collegestudentservice.repository.StudentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

@Service
public class StudentService {


    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository)
    {
        this.studentRepository = studentRepository;
    }


    public Student create(JsonNode requestBody) {
        Student student = new Student();

        student.setFirstName(requestBody.get("firstName").asText());
        student.setLastName(requestBody.get("lastName").asText());
        student.setEmail(requestBody.get("email").asText());
        student.setMarks(requestBody.get("marks").asText());

        College college = new College();

        college.setCollegeName(requestBody.get("collegeName").asText());
        college.setAddress(requestBody.get("address").asText());
       college.setGrade(requestBody.get("grade").asText());

        college.setStudent(student);
        student.setCollege(college);

        return studentRepository.save(student);
    }
}
