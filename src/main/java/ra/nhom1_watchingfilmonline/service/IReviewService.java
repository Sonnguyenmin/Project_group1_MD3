package ra.nhom1_watchingfilmonline.service;

import ra.nhom1_watchingfilmonline.model.entity.Reviews;

import java.util.List;

public interface IReviewService {
    List<Reviews> getAllReviews();
    Reviews getReviewById(Integer id);
    Boolean saveReview(Reviews reviews);
    Boolean updateReview(Reviews reviews);
    Boolean deleteReview(Integer id);
    List<Reviews>sortReviewsByRating();
    List<Reviews> getReviewByFilmId(Integer filmId);
}
