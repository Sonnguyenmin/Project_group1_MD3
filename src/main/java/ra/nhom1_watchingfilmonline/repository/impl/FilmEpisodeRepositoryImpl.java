package ra.nhom1_watchingfilmonline.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.nhom1_watchingfilmonline.model.entity.Banners;
import ra.nhom1_watchingfilmonline.model.entity.FilmEpisode;
import ra.nhom1_watchingfilmonline.repository.FilmEpisodeRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FilmEpisodeRepositoryImpl implements FilmEpisodeRepository {
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<FilmEpisode> findAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("from FilmEpisode ", FilmEpisode.class).getResultList();
    }

    @Override
    public FilmEpisode findById(Integer filmEpisodeId) {
        Session session = sessionFactory.openSession();
        return session.find(FilmEpisode.class, filmEpisodeId);
    }

    @Override
    public void save(FilmEpisode filmEpisode) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            if (filmEpisode.getFilmEpisodeId() == null) {
                session.save(filmEpisode);
            } else {
                session.update(filmEpisode);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public Boolean delete(Integer filmEpisodeId) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(findById(filmEpisodeId));
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

    public String getImageById(Integer filmEpisodeId) {
        Session session = sessionFactory.openSession();
        try {
            return (String) session.createQuery("select f.filmEpisodeImage from FilmEpisode f where f.filmEpisodeId = :filmEpisodeId")
                    .setParameter("filmEpisodeId", filmEpisodeId).getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }
}
