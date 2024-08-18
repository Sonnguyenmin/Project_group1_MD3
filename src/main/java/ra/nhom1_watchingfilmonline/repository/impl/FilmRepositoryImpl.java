package ra.nhom1_watchingfilmonline.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ra.nhom1_watchingfilmonline.model.entity.Categories;
import ra.nhom1_watchingfilmonline.model.entity.Countries;

import ra.nhom1_watchingfilmonline.model.entity.Films;
import ra.nhom1_watchingfilmonline.repository.FirmRepository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FilmRepositoryImpl implements FirmRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Films> getFilmFindAll() {
        Session session = sessionFactory.openSession();
        try {
            session.createQuery("from Films", Films.class).list();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Films> findAll(int page, int size, String search) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "from Films";
            if (!search.isEmpty()) {
                hql += " fm where fm.filmName like concat('%',:search,'%')";
            }
            List<Films> films;
            if (search.isEmpty()) {
                films = session.createQuery(hql, Films.class)
                        .setFirstResult(page * size)
                        .setMaxResults(size)
                        .getResultList();

            } else {
                films = session.createQuery(hql, Films.class)
                        .setParameter("search", search)
                        .setFirstResult(page * size)
                        .setMaxResults(size)
                        .getResultList();
            }
            return films;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }


    @Override
    public void saveFilm(Films films) {
        Session session = sessionFactory.openSession();
        try {
            if (films.getFilmId() == null) {
                session.beginTransaction();
                session.save(films);
                session.getTransaction().commit();
            } else {
                session.beginTransaction();
                session.update(films);
                session.getTransaction().commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }


    @Override
    public Boolean deleteFilm(Integer filmId) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(getFilmById(filmId));
            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }


    @Override
    public Films getFilmById(Integer filmId) {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("select f from Films f join fetch f.categories c where f.filmId = :id", Films.class)
                    .setParameter("id", filmId)
                    .getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public String getImageById(Integer filmId) {
        Session session = sessionFactory.openSession();
        try {
            return (String) session.createQuery("select fm.image from Films fm where fm.filmId = :filmId")
                    .setParameter("filmId", filmId).getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }


    @Override
    public Boolean isFilmNameExists(String filmName) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            String hql = "select count(*) from Films where filmName = :filmName";
            Long count = (Long) session.createQuery(hql)
                    .setParameter("filmName", filmName)
                    .uniqueResult();
            session.getTransaction().commit();
            return count > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Films findFilmByName(String filmName) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            String hql = "from Films where filmName = :filmName";
            return (Films) session.createQuery(hql)
                    .setParameter("filmName", filmName)
                    .uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }


    @Override
    public Long totalAllFilm(String search) {
        Session session = sessionFactory.openSession();
        try {
            if (search.isEmpty()) {
                return session.createQuery("select count(fm) from Films fm", Long.class)
                        .getSingleResult();
            } else {
                return session.createQuery("select count(fm) from Films fm where fm.filmName like concat('%',:search,'%') ", Long.class)
                        .setParameter("search", search)
                        .getSingleResult();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Films> findAllByOrderByFilmNameAsc(int page, int size) {
        Session session = sessionFactory.openSession();
        try {
            // Tạo một truy vấn với phân trang
            return session.createQuery("select fm from Films fm order by fm.filmName asc", Films.class)
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .getResultList();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Films> findAllByOrderByFilmNameDesc(int page, int size) {
        Session session = sessionFactory.openSession();
        try {
            // Tạo một truy vấn với phân trang
            return session.createQuery("select fm from Films fm order by fm.filmName desc", Films.class)
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .getResultList();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Films> getAllFilms() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            return session.createQuery("FROM Films", Films.class).setMaxResults(12).list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Films findByIdWithCategories(Integer filmId) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            // Sử dụng HQL để tải phim cùng với các thể loại
            Films films = session.createQuery("SELECT f FROM Films f JOIN FETCH f.categories where " +
                            "f.filmId =: filmId", Films.class)
                    .setParameter("filmId", filmId)
                    .uniqueResult();
            session.getTransaction().commit();
            return films;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return null;
    }


    @Override
    public List<Films> findAllPhimBo() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        List<Films> phimBo = null;
        try {
            phimBo = session.createQuery("from Films f where f.seriesSingle = false ", Films.class).getResultList();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return phimBo;
    }

    @Override
    public List<Films> findAllPhimLe() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        List<Films> phimLe = null;
        try {
            phimLe = session.createQuery("from Films f where f.seriesSingle = true ", Films.class).getResultList();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return phimLe;
    }

    @Override
    public List<Films> getTop5RecommendedFilms() {
        Session session = sessionFactory.openSession();
        List<Films> topFilms = null;
        try {
            String hql = "SELECT f FROM Films f " +
                    "JOIN f.reviews r " + // Chỉ lấy phim có review
                    "GROUP BY f.filmId " +
                    "ORDER BY AVG(r.rating) DESC";
            topFilms = session.createQuery(hql, Films.class)
                    .setMaxResults(5)
                    .getResultList();
            // Loại bỏ các bản sao nếu có
            topFilms = topFilms.stream()
                    .distinct()
                    .collect(Collectors.toList());
        } finally {
            session.close();
        }
        return topFilms;
    }

    @Override
    public List<Films> findAllUserFilm(int page, int size, String search) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "select fm from Films fm join fetch fm.categories";
            String sql = "select fm from Films fm join fetch fm.categories where fm.filmName like concat('%',:search,'%')";

            List<Films> films;
            if (search.isEmpty()) {
                films = session.createQuery(hql, Films.class)
                        .setFirstResult(page * size)
                        .setMaxResults(size)
                        .getResultList();

            } else {
                films = session.createQuery(sql, Films.class)
                        .setParameter("search", search)
                        .setFirstResult(page * size)
                        .setMaxResults(size)
                        .getResultList();
            }
            return films;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }


    @Override
    public Long totalAllUFilm(String search) {
        Session session = sessionFactory.openSession();
        try {
            if (search.isEmpty()) {
                return session.createQuery("select count(fm) from Films fm join fetch fm.categories", Long.class)
                        .getSingleResult();
            } else {
                return session.createQuery("select count(fm) from Films fm join fetch fm.categories where fm.filmName like concat('%',:search,'%') ", Long.class)
                        .setParameter("search", search)
                        .getSingleResult();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Films> findAllByOrderByUFilmNameAsc(int page, int size) {
        Session session = sessionFactory.openSession();
        try {
            // Tạo một truy vấn với phân trang
            return session.createQuery("select fm from Films fm join fetch fm.categories order by fm.filmName asc", Films.class)
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .getResultList();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Films> findAllByOrderByUFilmNameDesc(int page, int size) {
        Session session = sessionFactory.openSession();
        try {
            // Tạo một truy vấn với phân trang
            return session.createQuery("select fm from Films fm join fetch fm.categories order by fm.filmName desc", Films.class)
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .getResultList();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Films> upcomingMovies() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        List<Films> upcomingMovies = null;
        try {
            upcomingMovies = session.createQuery("from Films f where f.status = 2 ", Films.class).setMaxResults(6).getResultList();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return upcomingMovies;
    }

    public List<Films> findFilmsByCategory(Integer categoryId) {
        Session session = sessionFactory.openSession();
        List<Films> films = null;
        try {
            session.beginTransaction();
//            films = session.createQuery("SELECT f FROM Films f JOIN FETCH f.categories  WHERE f.categories = :categoryId", Films.class)
//                    .setParameter("categoryId", categoryId)
//                    .getResultList();
            films = session.createNativeQuery("select f.*,c.* from films f join film_category fc on f.filmId = fc.filmId join categories c on fc.categoryId = c.categoryId where fc.categoryId = :categoryId", Films.class)
                    .setParameter("categoryId", categoryId)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
        return films;
    }
}

