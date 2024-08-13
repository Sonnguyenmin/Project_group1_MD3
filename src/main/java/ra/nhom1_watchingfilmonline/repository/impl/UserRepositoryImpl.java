package ra.nhom1_watchingfilmonline.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.nhom1_watchingfilmonline.model.entity.Roles;
import ra.nhom1_watchingfilmonline.model.entity.Users;
import ra.nhom1_watchingfilmonline.repository.IRoleRepository;
import ra.nhom1_watchingfilmonline.repository.IUserRepository;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public Users findByOrEmailOrPhone(String email, String phone) {
        Session session = sessionFactory.openSession();
        Users user = null;
        try {
            session.beginTransaction();
            String hql = "from Users where email = :email or phone = :phone";
            user = (Users) session.createQuery(hql)
                    .setParameter("email", email)
                    .setParameter("phone", phone)
                    .uniqueResult();
            session.getTransaction().commit();
        }catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return user;
    }


    @Override
    public Users findUsersByUsername(String email) {
        Session session = sessionFactory.openSession();
        Users user = null;
        try {
            session.beginTransaction();
            String hql = "from Users where email = :email";
            user = (Users) session.createQuery(hql)
                    .setParameter("email", email)
                    .uniqueResult();
            session.getTransaction().commit();

        }catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return user;
    }

    @Override
    public Users save(Users user) {
        Session session = sessionFactory.openSession();
        // Kiểm tra tài khoản đã tồn tại
        Users existingUser = userRepository.findByOrEmailOrPhone(user.getEmail(), user.getPhone());
        if (existingUser != null) {
            throw new RuntimeException("User with email or phone already exists");
        }

        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public Users registerUser(String userName, String fullName, String email, String phone, String password, Integer roleId) {
        // Tìm vai trò theo roleId
        // Kiểm tra tài khoản đã tồn tại
        Users existingUser = userRepository.findByOrEmailOrPhone(email, phone);
        if (existingUser != null) {
            throw new RuntimeException("User with email or phone already exists");
        }

        Roles role = roleRepository.findRolesByRoleName(roleId == 1 ? "ADMIN" : "USER");

        if (role == null) {
            throw new RuntimeException("Role not found");
        }
        // Tạo người dùng mới
        Users user = new Users();
        user.setUserName(userName);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setAvatar(""); // Set default avatar if necessary
        user.setAddress(""); // Set default address if necessary
        user.setStatus(true); // Active by default
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setRoles(Arrays.asList(role)); // Sử dụng Arrays.asList để tạo danh sách
        return save(user);
    }

    @Override
    public List<Users> findAllUsers() {
        Session session = sessionFactory.openSession();
        List<Users> users = null;
        try {
            session.beginTransaction();
            String hql = "from Users"; // Truy vấn HQL để lấy tất cả người dùng
            users = session.createQuery(hql, Users.class).list(); // Thực thi truy vấn và lấy danh sách người dùng
            session.getTransaction().commit();
        }catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return users;
    }
}
