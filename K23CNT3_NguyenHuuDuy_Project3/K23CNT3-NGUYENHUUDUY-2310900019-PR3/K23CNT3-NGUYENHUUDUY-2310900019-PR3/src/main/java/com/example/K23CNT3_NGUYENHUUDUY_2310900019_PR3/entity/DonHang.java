package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "don_hang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String maDonHang;
    private LocalDateTime ngayDat;
    private double tongTien;

    private String tenNguoiNhan;
    private String soDienThoaiNhan;
    private String diaChiNhan;
    private String trangThai;

    @ManyToOne
    @JoinColumn(name = "nguoi_dung_id")
    private NguoiDung nguoiDung;

    @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL)
    private List<DonHangChiTiet> chiTietDonHang;
}
