package ra.nhom1_watchingfilmonline.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.nhom1_watchingfilmonline.model.entity.Banners;

import java.util.List;

@Repository
public class BannerDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Banners> findAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("from Banners ", Banners.class).getResultList();
    }

    public Banners findById(Integer id) {
        Session session = sessionFactory.openSession();
        return session.find(Banners.class, id);
    }

    public void save(Banners banners) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            if (banners.getBannerId() == null) {
                session.save(banners);
            } else {
                session.update(banners);
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

    public String getImageById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            return (String) session.createQuery("select bn.bannerImg from Banners bn where bn.id = :id")
                    .setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }
}
