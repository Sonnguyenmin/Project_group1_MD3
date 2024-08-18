package ra.nhom1_watchingfilmonline.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.nhom1_watchingfilmonline.model.entity.Payments;
import ra.nhom1_watchingfilmonline.model.entity.Roles;
import ra.nhom1_watchingfilmonline.model.entity.Users;
import ra.nhom1_watchingfilmonline.repository.IUserRepository;
import ra.nhom1_watchingfilmonline.repository.impl.PaymentDao;
import ra.nhom1_watchingfilmonline.repository.impl.RoleRepositoryImpl;
import ra.nhom1_watchingfilmonline.repository.impl.UserRepositoryImpl;
import ra.nhom1_watchingfilmonline.service.IUserService;
import ra.nhom1_watchingfilmonline.service.UploadService;


import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private UploadService uploadService;
    @Autowired
    PaymentDao paymentDao;
    @Autowired
    RoleRepositoryImpl roleRepository;


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
    public Users registerUser(String userName, String fullName, String email, String phone, String password, Integer roleId,Boolean status) {
        return userRepository.registerUser(userName, fullName, email, phone, password, roleId,status);
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
//        ====duc====
        if (users.getAvatar() == null || users.getAvatar().isEmpty()) {
            users.setAvatar(userRepository.getImageById(users.getUserId()));
        }
        //        ====duc====

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

    @Override
    public void handleAddWallet(Users user, Integer money, HttpSession session) {
        user.setUserWallet(user.getUserWallet() + money);
        session.setAttribute("user", user);
        userRepository.update(user);
    }

    @Override
    public List<Users> AllUsers() {
        return userRepository.AllUsers();
    }

    @Override
    public Boolean handleUpdateAcc(Users user) {
        Payments payment = new Payments();
        payment.setUsers(user);
        payment.setPaymentDate(new Date());
        payment.setMoney(100000D);
//            case "MONTHLY - 100.000đ":
        if (user.getUserWallet() < 100000) {
            return false;
        }


        user.setUserWallet(user.getUserWallet() - 100000);
//                payment.setSubscriptionType(SubscriptionType.MONTHLY);
//                break;
        List<Roles> rolesList= user.getRoles();
        rolesList.add(roleRepository.findRolesByRoleName("VIP"));
        user.setRoles(rolesList);
        userRepository.update(user);
        paymentDao.save(payment);
        return true;
    }
}
