package ra.nhom1_watchingfilmonline.model.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.nhom1_watchingfilmonline.model.entity.Roles;
import ra.nhom1_watchingfilmonline.model.entity.Users;
import ra.nhom1_watchingfilmonline.service.IUserService;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userService.findUsersByUsername(username);
        if (user != null) {
            return CustomerUserDetail.builder()
                    .userName(user.getUserName())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .address(user.getAddress())
                    .fullName(user.getFullName())
                    .password(user.getPassword())
                    .authorities(mapToUserAuthorities(user.getRoles()))
                    .build();
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    private List<GrantedAuthority> mapToUserAuthorities(List<Roles> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName())) // assuming getRoleName() returns a String
                .collect(Collectors.toList());
    }

}
