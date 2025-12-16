package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.NguoiDung;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.VaiTro;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.NguoiDungRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.VaiTroRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NguoiDungService {

    private final NguoiDungRepository nguoiDungRepository;
    private final VaiTroRepository vaiTroRepository;
    private final PasswordEncoder passwordEncoder;

    public List<NguoiDung> findAll() {
        return nguoiDungRepository.findAll();
    }

    public Optional<NguoiDung> findById(Long id) {
        return nguoiDungRepository.findById(id);
    }

    public Optional<NguoiDung> findByTenDangNhap(String username) {
        return nguoiDungRepository.findByUsername(username);
    }

    public NguoiDung save(NguoiDung user) {

        // mã hóa mật khẩu
        user.setMatKhau(passwordEncoder.encode(user.getMatKhau()));

        // gán ROLE_USER mặc định
        VaiTro roleUser = vaiTroRepository.findByTen("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Chưa có ROLE_USER trong database"));

        user.setVaiTros(Set.of(roleUser));

        return nguoiDungRepository.save(user);
    }

    public void delete(Long id) {
        nguoiDungRepository.deleteById(id);
    }

    public long count() {
        return nguoiDungRepository.count();
    }
}
