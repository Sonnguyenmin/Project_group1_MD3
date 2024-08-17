package ra.nhom1_watchingfilmonline.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.nhom1_watchingfilmonline.model.entity.Reviews;
import ra.nhom1_watchingfilmonline.repository.IReviewRepository;

import java.util.List;

@Repository
public class ReviewRepositoryImpl implements IReviewRepository {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public List<Reviews> getAllReviews() {
        Session session = sessionFactory.openSession();
        List<Reviews> reviewsList = null;
        try {
            session.beginTransaction();
            reviewsList = session.createCriteria(Reviews.class).list();
            session.getTransaction().commit();
            return reviewsList;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Reviews getReviewById(Integer id) {
        Session session = sessionFactory.openSession();
        Reviews reviews = null;
        try {
            session.beginTransaction();
            reviews = session.createQuery("SELECT r FROM Reviews r LEFT JOIN FETCH r.films f " +
                    "LEFT JOIN FETCH r.users u" +
                    " where r.reviewId =: id",Reviews.class)
                    .setParameter("id",id)
                    .getSingleResult();
            session.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return reviews;
    }

    @Override
    public Boolean saveReview(Reviews reviews) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(reviews);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return false;
    }

    @Override
    public Boolean updateReview(Reviews reviews) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(reviews);
            session.getTransaction().commit();
            return true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean deleteReview(Integer id) {
        return null;
    }

    @Override
    public List<Reviews> sortReviewsByRating() {
        return null;
    }

    @Override
    public List<Reviews> getReviewByFilmId(Integer filmId) {
        Session session = sessionFactory.openSession();
        List<Reviews> reviewsList = null;
        try {
            session.beginTransaction();
            reviewsList = session.createQuery("select r from Reviews r WHERE r.films.filmId=: filmId",Reviews.class)
                    .setParameter("filmId",filmId)
                    .getResultList();
            session.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return reviewsList;
    }
}
