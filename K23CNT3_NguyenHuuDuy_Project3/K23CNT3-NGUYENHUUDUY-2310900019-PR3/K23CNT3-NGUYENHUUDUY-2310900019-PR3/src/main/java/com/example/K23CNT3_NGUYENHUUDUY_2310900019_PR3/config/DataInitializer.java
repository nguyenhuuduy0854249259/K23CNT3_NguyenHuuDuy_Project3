package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.config;


import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.NguoiDung;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.VaiTro;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.NguoiDungRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.VaiTroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initData(
            NguoiDungRepository nguoiDungRepository,
            VaiTroRepository vaiTroRepository
    ) {
        return args -> {

            /* =====================
               1. Tạo vai trò
               ===================== */
            VaiTro adminRole = vaiTroRepository.findByTen("ADMIN")
                    .orElseGet(() -> vaiTroRepository.save(
                            VaiTro.builder()
                                    .ten("ADMIN")
                                    .build()
                    ));

            VaiTro userRole = vaiTroRepository.findByTen("USER")
                    .orElseGet(() -> vaiTroRepository.save(
                            VaiTro.builder()
                                    .ten("USER")
                                    .build()
                    ));

            /* =====================
               2. Tạo tài khoản ADMIN
               ===================== */
            if (nguoiDungRepository.findByTenDangNhap("admin").isEmpty()) {
                NguoiDung admin = NguoiDung.builder()
                        .tenDangNhap("admin")
                        .matKhau(passwordEncoder.encode("123456"))
                        .hoTen("Quản trị viên")
                        .email("admin@gmail.com")
                        .soDienThoai("0900000000")
                        .diaChi("Hệ thống")
                        .kichHoat(true)
                        .vaiTros(Set.of(adminRole))
                        .build();

                nguoiDungRepository.save(admin);
            }
            // ==================================
            /* =====================
               3. Tạo tài khoản USER
               ===================== */
            if (nguoiDungRepository.findByTenDangNhap("user").isEmpty()) {
                NguoiDung user = NguoiDung.builder()
                        .tenDangNhap("user")
                        .matKhau(passwordEncoder.encode("123456"))
                        .hoTen("Người dùng thường")
                        .email("user@gmail.com")
                        .soDienThoai("0911111111")
                        .diaChi("Việt Nam")
                        .kichHoat(true)
                        .vaiTros(Set.of(userRole))
                        .build();

                nguoiDungRepository.save(user);
            }
        };
    }
}
