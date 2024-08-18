package ra.nhom1_watchingfilmonline.repository;

import ra.nhom1_watchingfilmonline.model.entity.Comments;

import java.util.List;

public interface ICommentRepository {
    List<Comments> getAllComments(int page, int size);
    Comments getCommentById(Integer id);
    Boolean addComment(Comments comment);
    Boolean updateComment(Comments comment);
    Boolean deleteComment(Integer id);
    List<Comments> searchCommentsByFilm(String filmName, int offset, int size);
    List<Comments> getCommentsByFilmId(Integer filmId);
    int countComment();
    int countCommentByFilmName(String filmName);
    List<Comments> sortCommentsByContent(String content, int offset, int size);
     int countCommentByContent(String content);


}
