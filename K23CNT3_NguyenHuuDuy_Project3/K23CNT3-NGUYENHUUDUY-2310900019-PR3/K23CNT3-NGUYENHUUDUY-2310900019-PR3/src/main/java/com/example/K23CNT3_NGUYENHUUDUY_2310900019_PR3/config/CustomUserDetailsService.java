package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.config;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.NguoiDung;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.NguoiDungRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final NguoiDungRepository nguoiDungRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Debug log để kiểm tra email
        System.out.println("Login attempt email: " + email);

        NguoiDung user = nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng: " + email));

        return new CustomUserDetails(user);
    }
}
