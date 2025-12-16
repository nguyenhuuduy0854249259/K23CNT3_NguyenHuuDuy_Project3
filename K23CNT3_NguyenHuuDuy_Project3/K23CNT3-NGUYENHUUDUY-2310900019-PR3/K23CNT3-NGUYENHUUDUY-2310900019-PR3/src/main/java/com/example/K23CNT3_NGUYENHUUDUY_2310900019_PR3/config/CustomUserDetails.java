package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.config;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.NguoiDung;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final NguoiDung user;

    public CustomUserDetails(NguoiDung user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getVaiTros().stream()
                .map(v -> new SimpleGrantedAuthority("ROLE_" + v.getTen())) // ⭐ SỬA
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getMatKhau();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // đăng nhập bằng EMAIL
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

//    @Override
//    public boolean isEnabled() {
//        return user.isKichHoat();
//    }
}
