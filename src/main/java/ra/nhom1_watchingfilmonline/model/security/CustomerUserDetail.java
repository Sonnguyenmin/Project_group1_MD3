package ra.nhom1_watchingfilmonline.model.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.List;
@Service
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerUserDetail implements UserDetails {
    private String userName;      // Tên đăng nhập
    private String email;         // Email
    private String fullName;      // Họ và tên
    private String password;      // Mật khẩu
    private String avatar;        // Hình đại diện
    private String phone;         // Số điện thoại
    private String address;       // Địa chỉ
    private Boolean status;
    List<GrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
