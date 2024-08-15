package ra.nhom1_watchingfilmonline.repository.impl;


import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.nhom1_watchingfilmonline.model.dto.FilmDto;
import ra.nhom1_watchingfilmonline.model.entity.Films;
import ra.nhom1_watchingfilmonline.repository.FirmRepository;


import java.util.List;

@Repository
@RequiredArgsConstructor
public class FilmRepositoryImpl implements FirmRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Films> findAll(int page, int size, String search) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "from Films";
            if (!search.isEmpty()) {
                hql += " fm where fm.filmName like concat('%',:search,'%')";
            }
            List<Films> films;
            if(search.isEmpty()) {
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
    public void saveFilm (Films films) {
        Session session = sessionFactory.openSession();
        try {
            if (films.getFilmId() == null) {
                session.beginTransaction();
                session.save(films);
                session.getTransaction().commit();
            } else  {
                session.beginTransaction();
                session.update(films);
                session.getTransaction().commit();
            }
        }  catch (Exception ex) {
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
            return session.createQuery("select f from Films f join fetch f.categories c where f.filmId = :id",Films.class)
                    .setParameter("id",filmId)
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
        }
        finally {
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
        }
        finally {
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
    public FilmDto getFilmDTO(Integer filmId) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "select (f.filmId, f.filmName, c.categoryName) " +
                    "from Films f " +
                    "join f.categories c " +
                    "where f.filmId = :filmId";
            List<FilmDto> result = session.createQuery(hql, FilmDto.class)
                    .setParameter("filmId", filmId)
                    .getResultList();
            return result.isEmpty() ? null : result.get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
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
             phimBo = session.createQuery("from Films f where f.seriesSingle = true ", Films.class).getResultList();
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
            phimLe = session.createQuery("from Films f where f.seriesSingle = false ", Films.class).getResultList();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return phimLe;
    }



}
