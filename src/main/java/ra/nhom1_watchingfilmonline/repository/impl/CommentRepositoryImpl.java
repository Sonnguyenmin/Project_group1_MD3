package ra.nhom1_watchingfilmonline.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.nhom1_watchingfilmonline.model.entity.Comments;
import ra.nhom1_watchingfilmonline.model.entity.Films;
import ra.nhom1_watchingfilmonline.model.entity.Users;
import ra.nhom1_watchingfilmonline.repository.ICommentRepository;

import java.util.Collections;
import java.util.List;
@Repository
public class CommentRepositoryImpl implements ICommentRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Comments> getAllComments(int page, int size) {
        Session session = sessionFactory.openSession();
        List<Comments> comments = null;
        try {
            session.beginTransaction();

            // Tính toán vị trí bắt đầu và số lượng kết quả cần lấy
            int startIndex = (page - 1) * size;

            // Truy vấn để lấy tất cả các bình luận kèm theo thông tin người dùng và phim
            comments = session.createQuery("SELECT c FROM Comments c" +
                            " LEFT JOIN FETCH c.users " +
                            " LEFT JOIN FETCH c.films", Comments.class)
                    .setFirstResult(startIndex)
                    .setMaxResults(size)
                    .getResultList();

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

        try {
            session.beginTransaction();
            Comments existingComment = session.get(Comments.class, comment.getCommentId());
            if (existingComment == null) {
                // Nếu bình luận không tồn tại, không thể cập nhật
                return false;
            }
            // Cập nhật nội dung bình luận
            existingComment.setContent(comment.getContent());
            session.update(existingComment);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();

        } finally {
            session.close();
        }
       return false;
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
    public List<Comments> searchCommentsByFilm(String filmName, int offset, int size) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            // Truy vấn HQL để lấy danh sách bình luận theo tên phim
            Query<Comments> query = session.createQuery(
                    "SELECT c FROM Comments c JOIN c.films f WHERE f.filmName = :filmName",
                    Comments.class);
            query.setParameter("filmName", filmName);
            query.setFirstResult(offset);
            query.setMaxResults(size);
            List<Comments> comments = query.getResultList();
            session.getTransaction().commit();
            return comments;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
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

    @Override
    public int countComment() {
        Session session = sessionFactory.openSession();
        Long count = 0L;
        try {
            session.beginTransaction();
            count = (Long) session.createQuery("SELECT COUNT(c.commentId) FROM Comments c").getSingleResult();
            session.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();

        }finally {
            session.close();
        }
        return count.intValue();
    }

    @Override
    public int countCommentByFilmName(String filmName) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            // Truy vấn HQL để đếm số lượng bình luận theo tên phim
            Long count = (Long) session.createQuery(
                            "SELECT COUNT(c.commentId) FROM Comments c JOIN c.films f WHERE f.filmName = :filmName")
                    .setParameter("filmName", filmName)
                    .uniqueResult();
            session.getTransaction().commit();
            return count != null ? count.intValue() : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }



    @Override
    public List<Comments> sortCommentsByContent(String content, int offset, int size) {
        Session session = sessionFactory.openSession();
        List<Comments> comments = null;
        try {
            session.beginTransaction();
            comments = session.createQuery(
                            "SELECT c FROM Comments c WHERE c.content LIKE :content ORDER BY c.content ASC", Comments.class)
                    .setParameter("content", "%" + content + "%") // Sử dụng LIKE để tìm kiếm nội dung
                    .setFirstResult(offset)
                    .setMaxResults(size)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return comments;
    }

    @Override
    public int countCommentByContent(String content) {
        Session session = sessionFactory.openSession();
        Long count = null;
        try {
            session.beginTransaction();
            count = (Long) session.createQuery(
                            "SELECT COUNT(c.commentId) FROM Comments c WHERE c.content LIKE :content")
                    .setParameter("content", "%" + content + "%")
                    .getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return count != null ? count.intValue() : 0;
    }


}
