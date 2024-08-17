package ra.nhom1_watchingfilmonline.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.nhom1_watchingfilmonline.model.entity.Countries;
import ra.nhom1_watchingfilmonline.model.entity.Films;

import java.util.List;

@Repository
public class CountryDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Countries> findAllCountries() {
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


    public List<Countries> findAll(int page, int size, String search) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "from Countries";
            if (!search.isEmpty()) {
                hql += " c where c.countryName like concat('%',:search,'%')";
            }
            List<Countries> countries;
            if(search.isEmpty()) {
                countries = session.createQuery(hql, Countries.class)
                        .setFirstResult(page * size)
                        .setMaxResults(size)
                        .getResultList();

            } else {
                countries = session.createQuery(hql, Countries.class)
                        .setParameter("search", search)
                        .setFirstResult(page * size)
                        .setMaxResults(size)
                        .getResultList();
            }
            return countries;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }


    public Long totalAllCountry(String search) {
        Session session = sessionFactory.openSession();
        try {
            if (search.isEmpty()) {
                return session.createQuery("select count(c) from Countries c", Long.class)
                        .getSingleResult();
            } else {
                return session.createQuery("select count(c) from Countries c where c.countryName like concat('%',:search,'%') ", Long.class)
                        .setParameter("search", search)
                        .getSingleResult();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    public List<Countries> findAllByOrderByCountryNameAsc(int page, int size) {
        Session session = sessionFactory.openSession();
        try {
            // Tạo một truy vấn với phân trang
            return session.createQuery("select c from Countries c order by c.countryName asc", Countries.class)
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .getResultList();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }

    public List<Countries> findAllByOrderByCountryNameDesc(int page, int size) {
        Session session = sessionFactory.openSession();
        try {
            // Tạo một truy vấn với phân trang
            return session.createQuery("select c from Countries c order by c.countryName desc", Countries.class)
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .getResultList();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }

}
