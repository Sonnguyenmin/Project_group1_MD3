package ra.nhom1_watchingfilmonline.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.nhom1_watchingfilmonline.model.entity.Users;
import ra.nhom1_watchingfilmonline.repository.IUserRepository;
import ra.nhom1_watchingfilmonline.service.IUserService;


import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;


    @Override
    public Users findByOrEmailOrPhone(String email, String phone) {
        return userRepository.findByOrEmailOrPhone(email, phone);
    }


    @Override
    public Users findUsersByUsername(String mail) {
        return userRepository.findUsersByUsername(mail);
    }

    @Override
    public Users save(Users user) {
        return userRepository.save(user);
    }

    @Override
    public Users registerUser(String userName, String fullName, String email, String phone, String password, Integer roleId) {
        return userRepository.registerUser(userName, fullName, email, phone, password, roleId);
    }

//    @Override
//    public List<Users> findAllUsers() {
//        return userRepository.findAllUsers();
//    }

    @Override
    public String getCurrentUserName() {
        return userRepository.getCurrentUserName();
    }

    @Override
    public Boolean update(Users users) {
        return userRepository.update(users);
    }

    @Override
    public Users findUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public List<Users> findAllUsers(int page, int size, String search) {
        return userRepository.findAllUsers(page, size, search);
    }

    @Override
    public Long totalAllUser(String search) {
        return userRepository.totalAllUser(search);
    }

    @Override
    public List<Users> findAllByOrderByUserAsc(int page, int size) {
        return userRepository.findAllByOrderByUserAsc(page, size);
    }

    @Override
    public List<Users> findAllByOrderByUserDesc(int page, int size) {
        return userRepository.findAllByOrderByUserDesc(page, size);
    }

    @Override
    public String findPasswordByEmail(String email) {
      return userRepository.findPasswordByEmail(email);
    }


}
