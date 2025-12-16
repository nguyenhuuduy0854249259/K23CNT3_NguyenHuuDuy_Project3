package com.example.k23cnt3_nhd_bt3.Controller;

import com.example.k23cnt3_nhd_bt3.Entity.nhdStudent;
import com.example.k23cnt3_nhd_bt3.service.nhdServiceStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class nhdStudentController {
    @Autowired
    private nhdServiceStudent studentService;
    @GetMapping("/student-list")
    public List<nhdStudent> getAllStudents() {
        return studentService.getStudents();
    }
    @GetMapping("/student/{id}")
    public nhdStudent getAllStudents(@PathVariable String id)
    {
        Long param = Long.parseLong(id);
        return studentService.getStudent(param);
    }
    @PostMapping("/student-add")
    public nhdStudent addStudent(@RequestBody nhdStudent student)
    {
        return studentService.addStudent(student);
    }
    @PutMapping("/student/{id}")
    public nhdStudent updateStudent(@PathVariable String id,
                                 @RequestBody nhdStudent student) {
        Long param = Long.parseLong(id);
        return studentService.updateStudent(param,
                student);
    }
    @DeleteMapping("/student/{id}")
    public boolean deleteStudent(@PathVariable String id) {
        Long param = Long.parseLong(id);
        return studentService.deleteStudent(param);
    }
}