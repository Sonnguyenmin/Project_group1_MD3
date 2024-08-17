package ra.nhom1_watchingfilmonline.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;        // id người dùng, kiểu Int trong cơ sở dữ liệu

    @Column(nullable = false,unique = true)
    private String userName;      // Tên đăng nhập

    @Column(nullable = false,unique = true)
    private String email;// Email

    @Column(nullable = false)
    private String fullName;      // Họ và tên

    @Column(nullable = false)
    private String password;      // Mật khẩu

    @Column(nullable = false)
    private String avatar;        // Hình đại diện

    @Column(nullable = false)
    private String phone;         // Số điện thoại

    @Column(nullable = false)
    private String address;// Địa chỉ

    @Column(nullable = false)
    private Boolean status = true;       // Giá trị mặc định là true (Active)

    @Column(nullable = false)
    private Date createdAt;// Thời gian tạo

    @Column(nullable = false)
    private Date updatedAt;// Thời gian cập nhật
    @Min(0)
    private Integer userWallet=0;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private List<Roles> roles;

}
