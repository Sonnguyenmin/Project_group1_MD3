package ra.nhom1_watchingfilmonline.repository.impl;


import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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
    public List<Films> findAll() {
//        Integer offset, Integer size
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        List<Films> films = null;
        try {
            Query<Films> query = session.createQuery("from Films", Films.class);
//            query.setFirstResult(offset);
//            query.setMaxResults(size);
            films = query.getResultList();
            tx.commit();
        } catch (Exception ex) {
         tx.rollback();
         ex.printStackTrace();
        } finally {
            session.close();
        }
        return films;
    }

//    @Override
//    public Integer getTotalFilms() {
//        Session session = sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        Integer totalFilms = null;
//        try {
//            Query<Integer> query = session.createQuery("select count(fm) from Films fm", Integer.class);
//            totalFilms = query.uniqueResult();
//            tx.commit();
//        } catch (Exception ex) {
//            tx.rollback();
//            ex.printStackTrace();
//        } finally {
//            session.close();
//        }
//        return totalFilms;
//    }

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
            Films films = session.get(Films.class, filmId);
            return films;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Films> searchFilm(String filmName) {
        Session session = sessionFactory.openSession();
        try {
            if (filmName == null || filmName.isEmpty()) {
                filmName = "%";
            }
            else
                filmName = "%" + filmName + "%";
            List list = session.createQuery("from Films where filmName like :filmName")
                    .setParameter("filmName", filmName)
                            .list();
            return list;
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
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
    public List<Films> sortByFilmName() {
        Session session = sessionFactory.openSession();
        List<Films> films = null;
        try {
            session.beginTransaction();
            String hql = "from Films order by filmName";
            Query<Films> query = session.createQuery(hql, Films.class);
            films = query.getResultList();
            session.getTransaction().commit();
            return films;
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return films;
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


}
