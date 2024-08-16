package ra.nhom1_watchingfilmonline.repository;

import ra.nhom1_watchingfilmonline.model.entity.Comments;
import ra.nhom1_watchingfilmonline.model.entity.Reviews;

import java.util.List;

public interface IReviewRepository {
    List<Reviews> getAllReviews();
    Reviews getReviewById(Integer id);
    Boolean saveReview(Reviews reviews);
    Boolean updateReview(Reviews reviews);
    Boolean deleteReview(Integer id);
    List<Reviews>sortReviewsByRating();
    public List<Reviews> getReviewByFilmId(Integer filmId);
}
