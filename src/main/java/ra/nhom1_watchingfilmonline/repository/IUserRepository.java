package ra.nhom1_watchingfilmonline.repository;



import ra.nhom1_watchingfilmonline.model.entity.Categories;
import ra.nhom1_watchingfilmonline.model.entity.Films;

import ra.nhom1_watchingfilmonline.model.entity.Users;

import java.util.List;

public interface IUserRepository {
    Users findByOrEmailOrPhone(String email, String phone);
    Users findUsersByUsername(String mail);
    Users save(Users user);
    Users registerUser(String userName, String fullName, String email, String phone, String password, Integer roleId);
    List<Users> findAllUsers(int page, int size, String search);

    Long totalAllUser(String search);

    List<Users> findAllByOrderByUserAsc(int page, int size);

    List<Users> findAllByOrderByUserDesc(int page, int size);

    Boolean update(Users users);
    Users findById(Integer id);

    String findPasswordByEmail(String email);

    String getCurrentUserName();

    List<Users> AllUsers();
}
