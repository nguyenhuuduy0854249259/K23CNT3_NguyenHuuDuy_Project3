package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "danh_gia",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"san_pham_id", "nguoi_dung_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanhGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer  soSao;

    @Column(columnDefinition = "TEXT")
    private String binhLuan;

    private LocalDateTime thoiGian;

    private boolean daDuyet; // ADMIN DUYỆT

    @ManyToOne
    @JoinColumn(name = "san_pham_id")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "nguoi_dung_id")
    private NguoiDung nguoiDung;
}


