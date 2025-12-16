package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lien_he")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LienHe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hoTen;
    private String email;
    private String soDienThoai;

    @Column(columnDefinition = "TEXT")
    private String noiDung;

    private LocalDateTime thoiGianGui;
}
