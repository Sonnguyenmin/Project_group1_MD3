package ra.nhom1_watchingfilmonline.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import ra.nhom1_watchingfilmonline.model.entity.Comments;
import ra.nhom1_watchingfilmonline.model.entity.Films;
import ra.nhom1_watchingfilmonline.model.entity.Users;
import ra.nhom1_watchingfilmonline.model.security.CustomerUserDetail;
import ra.nhom1_watchingfilmonline.repository.ICommentRepository;

import java.util.List;
@Repository
public class CommentRepositoryImpl implements ICommentRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Comments> getAllComments() {
         Session session = sessionFactory.openSession();
         List<Comments> comments = null;
         try {
             session.beginTransaction();
             // Truy vấn để lấy tất cả các bình luận kèm theo thông tin người dùng và phim
             comments = session.createQuery("SELECT c FROM Comments c LEFT JOIN FETCH c.users LEFT JOIN fetch c.films",
                     Comments.class ).getResultList();
             session.getTransaction().commit();
             return comments;
         }catch (Exception e){
             e.printStackTrace();
             session.getTransaction().rollback();
         }finally {
             session.close();
         }
        return null;
    }

    @Override
    public Comments getCommentById(Integer id) {
        Session session = sessionFactory.openSession();
        Comments comments = null;
        try {
            session.beginTransaction();
            comments = session.createQuery(
                            "SELECT c FROM Comments c LEFT JOIN FETCH c.users u" +
                                    " LEFT JOIN FETCH c.films f " +
                                    "WHERE c.commentId = :id", Comments.class)
                    .setParameter("id", id)
                    .getSingleResult();
            session.getTransaction().commit();


        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return comments;
    }


    @Override
    public Boolean addComment(Comments comment) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(comment);
            session.getTransaction().commit();
            return true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean updateComment(Comments comment) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Boolean result = false;
        try {
            transaction = session.beginTransaction();
            Comments existingComment = session.get(Comments.class, comment.getCommentId());
            if (existingComment == null) {
                return false;
            }
            Users user = session.get(Users.class, comment.getUsers().getUserId());
            if (user == null) {
                return false;
            }
            Films film = session.get(Films.class, comment.getFilms().getFilmId());
            if (film == null) {
                return false;
            }
            existingComment.setContent(comment.getContent());
            session.update(existingComment);
            transaction.commit();
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return result;
    }


    @Override
    public Boolean deleteComment(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Comments existingComment = session.get(Comments.class, id);
            if (existingComment != null) {
                session.delete(existingComment);
                session.getTransaction().commit();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
        return false;
    }


    @Override
    public List<Comments> searchCommentsByFilm(String filmName) {
        Session session = sessionFactory.openSession();
        List<Comments> comments = null;
        try {
            session.beginTransaction();
            comments = session.createQuery(
                            "SELECT c FROM Comments c JOIN c.films f WHERE f.filmName = :filmName", Comments.class)
                    .setParameter("filmName", filmName)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
        return comments;
    }

    @Override
    public List<Comments> getCommentsByFilmId(Integer filmId) {
        Session session = sessionFactory.openSession();
        List<Comments> comments = null;
        try {
            session.beginTransaction();
            // Thực hiện truy vấn HQL để lấy bình luận dựa trên filmId
            comments = session.createQuery(
                            "SELECT c FROM Comments c WHERE c.films.filmId = :filmId", Comments.class)
                    .setParameter("filmId", filmId)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
        return comments;
    }


}
