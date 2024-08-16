package ra.nhom1_watchingfilmonline.service;

import ra.nhom1_watchingfilmonline.model.entity.Comments;

import java.util.List;

public interface ICommentService {
    List<Comments> getAllComments();
    Comments getCommentById(Integer id);
    Boolean addComment(Comments comment);
    Boolean updateComment(Comments comment);
    Boolean deleteComment(Integer id);
    List<Comments> searchCommentsByFilm(String filmName);
    List<Comments> getCommentsByFilmId(Integer filmId);
}
