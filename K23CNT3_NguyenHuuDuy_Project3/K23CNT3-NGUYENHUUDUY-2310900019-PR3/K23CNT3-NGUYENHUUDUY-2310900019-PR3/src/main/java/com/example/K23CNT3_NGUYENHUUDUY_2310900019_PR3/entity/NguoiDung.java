package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "nguoi_dung")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class NguoiDung implements UserDetails { // Implement UserDetails chuẩn Spring Security

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tenDangNhap;

    @Column(nullable = false)
    private String matKhau;

    private String hoTen;
    private String soDienThoai;
    private String email;
    private String diaChi;

    private boolean kichHoat = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "nguoi_dung_vai_tro",
            joinColumns = @JoinColumn(name = "nguoi_dung_id"),
            inverseJoinColumns = @JoinColumn(name = "vai_tro_id")
    )
    private Set<VaiTro> vaiTros;

    // --- QUAN TRỌNG: Setter thủ công cho mật khẩu ---
    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getMatKhau() {
        return this.matKhau;
    }

    // --- QUAN TRỌNG: Xử lý quyền hạn tránh lỗi Null ---
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (vaiTros == null) {
            return Collections.emptyList();
        }
        return vaiTros.stream()
                .map(vt -> new SimpleGrantedAuthority(vt.getTen()))
                .toList();
    }

    @Override
    public String getPassword() {
        return this.matKhau;
    }

    @Override
    public String getUsername() {
        return this.tenDangNhap;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return kichHoat; }
}