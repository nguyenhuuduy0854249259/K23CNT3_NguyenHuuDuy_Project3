package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    // Thêm trường này vào
    private String phuongThucThanhToan;
    private String trangThai;


    @ManyToOne
    @JoinColumn(name = "nguoi_dung_id")
    private NguoiDung nguoiDung;

    // Trong file DonHang.java
    @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL)
    @Builder.Default // Nếu bạn dùng @Builder của Lombok, phải có dòng này
    private List<DonHangChiTiet> donHangChiTiet = new ArrayList<>();
}
