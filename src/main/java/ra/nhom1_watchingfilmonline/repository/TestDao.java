package ra.nhom1_watchingfilmonline.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.nhom1_watchingfilmonline.model.entity.Test;

import java.util.List;

@Repository
public class TestDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Test> findAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("from Test", Test.class).list();
    }

    public Test findById(Integer id) {
        Session session = sessionFactory.openSession();
        return session.find(Test.class, id);
    }


    public void save(Test test) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        try{
            if (test.getId() != null) {
                session.update(test);
            } else {
                session.save(test);
            }
            transaction.commit();
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }

    }

    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        session.delete(findById(id));
    }

    public String getImageById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            return (String) session.createQuery("select f.image from Test f where f.id = :id")
                    .setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }
}
