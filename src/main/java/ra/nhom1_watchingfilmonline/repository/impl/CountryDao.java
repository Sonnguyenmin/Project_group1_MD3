package ra.nhom1_watchingfilmonline.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.nhom1_watchingfilmonline.model.entity.Countries;

import java.util.List;

@Repository
public class CountryDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Countries> findAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("from Countries ", Countries.class).getResultList();
    }

    public Countries findById(Integer id) {
        Session session = sessionFactory.openSession();
        return session.find(Countries.class, id);
    }

    public void save(Countries countries) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            if (countries.getCountryId() == null) {
                session.save(countries);
            } else {
                session.update(countries);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(findById(id));
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }

    }

}
