package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository;


import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, Long> {
    // Danh mục cha (menu cấp 1)
    List<DanhMuc> findByParentIsNull();
}
