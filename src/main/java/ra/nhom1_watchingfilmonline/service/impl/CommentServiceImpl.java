package ra.nhom1_watchingfilmonline.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.nhom1_watchingfilmonline.model.entity.Comments;
import ra.nhom1_watchingfilmonline.repository.ICommentRepository;
import ra.nhom1_watchingfilmonline.service.ICommentService;

import java.util.List;
@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private ICommentRepository commentRepository;
    @Override
    public List<Comments> getAllComments() {
        return commentRepository.getAllComments() ;
    }

    @Override
    public Comments getCommentById(Integer id) {
        return commentRepository.getCommentById(id) ;
    }

    @Override
    public Boolean addComment(Comments comment) {
        return commentRepository.addComment(comment);
    }

    @Override
    public Boolean updateComment(Comments comment) {
        return commentRepository.updateComment(comment);
    }

    @Override
    public Boolean deleteComment(Integer id) {
        return commentRepository.deleteComment(id);
    }

    @Override
    public List<Comments> searchCommentsByFilm(String filmName) {
        return commentRepository.searchCommentsByFilm(filmName);
    }

    @Override
    public List<Comments> getCommentsByFilmId(Integer filmId) {
        return commentRepository.getCommentsByFilmId(filmId);
    }
}
