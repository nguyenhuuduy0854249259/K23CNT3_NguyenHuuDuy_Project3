package com.example.k23cnt3_nhd_lesson06.controller;

import com.example.k23cnt3_nhd_lesson06.dto.StudentDTO;
import com.example.k23cnt3_nhd_lesson06.entity.Student;
import com.example.k23cnt3_nhd_lesson06.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Hiển thị danh sách tất cả sinh viên
    @GetMapping
    public String getStudents(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "student-list";
    }

    // Trang thêm sinh viên mới
    @GetMapping("/add-new")
    public String addNewStudent(Model model) {
        model.addAttribute("student", new Student());
        return "student-add";
    }

    // Trang chỉnh sửa sinh viên
    @GetMapping("/edit/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
        StudentDTO student = studentService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id: " + id));
        model.addAttribute("student", student);
        return "student-edit";
    }

    // Lưu sinh viên mới
    @PostMapping
    public String saveStudent(@ModelAttribute("student") StudentDTO student) {
        studentService.save(student);
        return "redirect:/students";
    }

    // Cập nhật sinh viên
    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable(value = "id") Long id,
                                @ModelAttribute("student") StudentDTO student) {
        studentService.updateStudentById(id, student);
        return "redirect:/students";
    }

    // Xóa sinh viên
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable(value = "id") Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}
