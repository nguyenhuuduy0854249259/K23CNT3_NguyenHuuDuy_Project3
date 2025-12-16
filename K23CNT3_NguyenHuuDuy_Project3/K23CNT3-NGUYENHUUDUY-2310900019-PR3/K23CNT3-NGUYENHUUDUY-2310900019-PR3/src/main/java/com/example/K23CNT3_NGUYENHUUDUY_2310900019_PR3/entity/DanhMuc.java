package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "danh_muc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanhMuc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ten;

    @Column(columnDefinition = "TEXT")
    private String moTa;

    // ===== CHA =====
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private DanhMuc parent;

    // ===== CON =====
    @OneToMany(mappedBy = "parent")
    private List<DanhMuc> children;

    // ===== SẢN PHẨM =====
    @OneToMany(mappedBy = "danhMuc")
    private List<SanPham> sanPhams;
}
