package ra.nhom1_watchingfilmonline.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.nhom1_watchingfilmonline.model.entity.Roles;
import ra.nhom1_watchingfilmonline.repository.IRoleRepository;

@Repository
public class RoleRepositoryImpl implements IRoleRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Roles findRolesByRoleName(String roleName) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Roles roles = (Roles) session.createQuery("from Roles WHERE roleName =:roleName")
                    .setParameter("roleName", roleName)
                    .getSingleResult();
            session.getTransaction().commit();
            return roles;
        }catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return null;
    }
}
