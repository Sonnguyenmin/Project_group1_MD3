package ra.nhom1_watchingfilmonline.model.dto;

import lombok.*;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    @NotBlank(message = "Tên tài khoản không được để trống,")
    @Size(min = 6, max = 100, message = " Có ít nhất 6 ký tự và không quá 100 ký tự")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = " Và chỉ được chứa chữ cái và số, không có ký tự đặc biệt")
    private String userName;      // Tên đăng nhập

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Định dạng email không hợp lệ")
    private String email;         // Email

    @NotBlank(message = "Họ và tên không được để trống")
    private String fullName;      // Họ và tên

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;      // Mật khẩu

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^0[1-9][0-9]{8,9}$", message = "Vui lòng nhập số điện thoại Việt Nam hợp lệ.")
    private String phone;         // Số điện thoại

    @NotBlank(message = "Mật khẩu không khớp, vui lòng nhập lại mật khẩu ")
    private String confirmPassword; // Nhập lại mật khẩu
}
