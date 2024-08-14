package ra.nhom1_watchingfilmonline.service;

import ra.nhom1_watchingfilmonline.model.entity.Users;

import java.util.List;

public interface IUserService {
    Users findByOrEmailOrPhone(String email, String phone);
    Users findUsersByUsername(String mail);
    Users save(Users user);
    Users registerUser(String userName, String fullName, String email, String phone, String password, Integer roleId);
    List<Users> findAllUsers();

    Boolean update(Users users);
    Users findUserById(Integer id);
    String findPasswordByEmail(String email);
}

