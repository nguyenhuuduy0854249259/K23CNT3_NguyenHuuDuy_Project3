package com.example.k23cnt3_nhd_lesson06.repository;

import com.example.k23cnt3_nhd_lesson06.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends
        JpaRepository<Student, Long> {
}