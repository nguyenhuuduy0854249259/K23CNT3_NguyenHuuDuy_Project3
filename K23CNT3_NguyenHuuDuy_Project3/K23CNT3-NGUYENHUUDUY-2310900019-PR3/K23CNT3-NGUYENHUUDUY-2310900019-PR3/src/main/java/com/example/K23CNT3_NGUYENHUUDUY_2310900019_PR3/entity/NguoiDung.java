package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "nguoi_dung")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class NguoiDung {

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

    // ✅ ĐẶT ĐÚNG VỊ TRÍ
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "nguoi_dung_vai_tro",
            joinColumns = @JoinColumn(name = "nguoi_dung_id"),
            inverseJoinColumns = @JoinColumn(name = "vai_tro_id")
    )
    private Set<VaiTro> vaiTros;

    // ✅ LẤY ROLE TỪ DATABASE
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return vaiTros.stream()
                .map(vt -> new SimpleGrantedAuthority(vt.getTen()))
                .toList();
    }
}

