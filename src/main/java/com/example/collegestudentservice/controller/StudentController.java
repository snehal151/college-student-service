package com.example.collegestudentservice.controller;

import com.example.collegestudentservice.model.Student;
import com.example.collegestudentservice.service.StudentService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")

public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(@RequestBody JsonNode requestBody){
        return studentService.create(requestBody);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable  Integer id)
    {
        return studentService.findById(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable  Integer id){
        studentService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
