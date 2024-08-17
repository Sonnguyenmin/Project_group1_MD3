package ra.nhom1_watchingfilmonline.service;

import com.google.cloud.storage.Acl;
import ra.nhom1_watchingfilmonline.model.entity.Films;
import ra.nhom1_watchingfilmonline.model.entity.Users;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IUserService {
    Users findByOrEmailOrPhone(String email, String phone);
    Users findUsersByUsername(String mail);
    Users save(Users user);
    Users registerUser(String userName, String fullName, String email, String phone, String password, Integer roleId);
    String getCurrentUserName();
    Boolean update(Users users);
    Users findUserById(Integer id);
    String findPasswordByEmail(String email);

    void handleAddWallet(Users user, Integer money, HttpSession session);


    List<Users> findAllUsers(int page, int size, String search);

    Long totalAllUser(String search);

    List<Users> findAllByOrderByUserAsc(int page, int size);

    List<Users> findAllByOrderByUserDesc(int page, int size);


    List<Users> AllUsers();

//    List<Users> findAllUsers();


}

