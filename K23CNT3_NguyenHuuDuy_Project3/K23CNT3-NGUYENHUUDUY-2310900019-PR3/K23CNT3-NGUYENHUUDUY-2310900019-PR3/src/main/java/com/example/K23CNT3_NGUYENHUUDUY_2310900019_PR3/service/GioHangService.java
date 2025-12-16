package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.GioHang;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.GioHangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GioHangService {

    private final GioHangRepository gioHangRepository;


    public Optional<GioHang> findByNguoiDung(Long userId) {
        return gioHangRepository.findByNguoiDungId(userId);
    }

    public GioHang save(GioHang gioHang) {
        return gioHangRepository.save(gioHang);
    }
}
