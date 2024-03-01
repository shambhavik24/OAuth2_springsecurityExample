package com.example.Securitydemoproject.controller;

import com.example.Securitydemoproject.entity.Student;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class StudentController {


    private List<Student> students = new ArrayList<>();
    @GetMapping("/students")
    public List<Student> students() {

       Student student1 = new Student();
       student1.setFirstName("Marsh");
       student1.setLastName("michael");

       Student student2 = new Student();
       student2.setFirstName("leo");
       student2.setLastName("Henry");
       students.add(student1);
       students.add(student2);
       return students;

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/students")
    public Student createStudent (@RequestBody Student student){
        students.add(student);
        return student;
    }

    @GetMapping("/home")
    public String home(){
         return "Home";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
