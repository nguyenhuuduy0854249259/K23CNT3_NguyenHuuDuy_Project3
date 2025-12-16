package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tin_tuc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TinTuc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tieuDe;
    private String anhDaiDien;

    @Column(columnDefinition = "TEXT")
    private String noiDung;

    private LocalDateTime thoiGian;
}
