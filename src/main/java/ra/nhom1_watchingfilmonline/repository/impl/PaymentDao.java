package ra.nhom1_watchingfilmonline.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.nhom1_watchingfilmonline.model.entity.Payments;

@Repository
public class PaymentDao {
    @Autowired
    SessionFactory sessionFactory;

    public void save(Payments payment) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(payment);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }
}
