package com.example.k23cnt3_nhd_bt3.service;

import com.example.k23cnt3_nhd_bt3.Entity.nhdStudent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service class: nhdServiceStudent
 * <p>Lớp dịch vụ thực hiện các chức năng thao tác với List Object nhdStudent</p>
 *
 * @author
 * @version 1.0
 */
@Service
public class nhdServiceStudent {

    // Danh sách sinh viên
    private final List<nhdStudent> students = new ArrayList<>();

    // Khởi tạo dữ liệu mẫu
    public nhdServiceStudent() {
        students.addAll(Arrays.asList(
                new nhdStudent("chungtrinhj@gmail.com", "0978611889", "Số 25 VNP", "Nam", 20, "Devmaster 1", 1L),
                new nhdStudent("contact@devmaster.edu.vn", "0978611889", "Số 25 VNP", "Nam", 25, "Devmaster 2", 2L),
                new nhdStudent("chungtrinhj@gmail.com", "0978611889", "Số 25 VNP", "Nam", 22, "Devmaster 3", 3L)
        ));
    }

    // Lấy toàn bộ danh sách sinh viên
    public List<nhdStudent> getStudents() {
        return students;
    }

    // Lấy sinh viên theo id
    public nhdStudent getStudent(Long id) {
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Thêm mới một sinh viên
    public nhdStudent addStudent(nhdStudent student) {
        students.add(student);
        return student;
    }

    // Cập nhật thông tin sinh viên
    public nhdStudent updateStudent(Long id, nhdStudent student) {
        nhdStudent existing = getStudent(id);
        if (existing == null) {
            return null;
        }
        existing.setName(student.getName());
        existing.setAddress(student.getAddress());
        existing.setEmail(student.getEmail());
        existing.setPhone(student.getPhone());
        existing.setAge(student.getAge());
        existing.setGender(student.getGender());
        return existing;
    }

    // Xóa thông tin sinh viên
    public boolean deleteStudent(Long id) {
        nhdStudent existing = getStudent(id);
        return existing != null && students.remove(existing);
    }
}
