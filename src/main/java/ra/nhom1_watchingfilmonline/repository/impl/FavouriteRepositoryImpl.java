package ra.nhom1_watchingfilmonline.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.nhom1_watchingfilmonline.model.entity.Favourite;
import ra.nhom1_watchingfilmonline.repository.IFavouriteRepository;

import java.util.List;
@Repository
public class FavouriteRepositoryImpl implements IFavouriteRepository {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public List<Favourite> getAllFavourites() {
        Session session = sessionFactory.openSession();
        List<Favourite> favourites = null;
        try {
            session.beginTransaction();
            favourites = session.createQuery("SELECT fa FROM Favourite fa" +
                    " LEFT JOIN FETCH fa.films left join fetch fa.users",Favourite.class)
                    .getResultList();
            session.getTransaction().commit();


        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return favourites;
    }

    @Override
    public Boolean addFavourite(Favourite favourite) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(favourite);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return false;
    }

    @Override
    public Boolean removeFavourite(Favourite favourite) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Favourite existingFavourite = getFavouriteById(favourite.getFavouriteId());
            if (existingFavourite != null) {
                session.delete(existingFavourite);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public Favourite getFavouriteById(Integer id) {
        Session session = sessionFactory.openSession();
        Favourite favourite = null;
        try {
            session.beginTransaction();
            List<Favourite> results = session.createQuery("SELECT fa FROM Favourite fa WHERE fa.favouriteId = :id", Favourite.class)
                    .setParameter("id", id)
                    .getResultList();
            if (!results.isEmpty()) {
                favourite = results.get(0);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return favourite;

    }


    @Override
    public List<Favourite> getFavouriteByFilmId(Integer filmId) {
        Session session = sessionFactory.openSession();
        List<Favourite> favourites = null;
        try {
            session.beginTransaction();
            favourites = session.createQuery("select fa from Favourite fa " +
                    "where fa.films.filmId =: filmId",Favourite.class)
                    .setParameter("filmId",filmId)
                    .getResultList();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return favourites;
    }

    @Override
    public List<Favourite> findByUser_UserId(Integer userId) {
        Session session = sessionFactory.openSession();
        List<Favourite> favourites = null;
        try {
            session.beginTransaction();
            // Tạo truy vấn HQL để tìm các đối tượng Favourite theo userId
            favourites = session.createQuery("SELECT fa FROM Favourite fa " +
                            "LEFT JOIN FETCH fa.films f " +
                            "LEFT JOIN FETCH fa.users u " +
                            "WHERE u.userId = :userId", Favourite.class)
                    .setParameter("userId", userId)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return favourites;
    }

    @Override
    public boolean isFavouriteExists(Integer filmId, Integer userId) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Long count = (Long) session.createQuery("SELECT COUNT(fa) FROM Favourite fa " +
                            "WHERE fa.films.filmId = :filmId AND fa.users.userId = :userId")
                    .setParameter("filmId", filmId)
                    .setParameter("userId", userId)
                    .getSingleResult();
            session.getTransaction().commit();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }

}
