package ra.nhom1_watchingfilmonline.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.nhom1_watchingfilmonline.model.entity.Reviews;
import ra.nhom1_watchingfilmonline.repository.IReviewRepository;
import ra.nhom1_watchingfilmonline.service.IReviewService;

import java.util.List;

@Service
public class IReviewServiceImpl implements IReviewService {
    @Autowired
    private IReviewRepository reviewRepository;
    @Override
    public List<Reviews> getAllReviews() {
        return reviewRepository.getAllReviews();
    }

    @Override
    public Reviews getReviewById(Integer id) {
        return reviewRepository.getReviewById(id);
    }

    @Override
    public Boolean saveReview(Reviews reviews) {
        return reviewRepository.saveReview(reviews);
    }

    @Override
    public Boolean updateReview(Reviews reviews) {
        return reviewRepository.updateReview(reviews);
    }

    @Override
    public Boolean deleteReview(Integer id) {
        return reviewRepository.deleteReview(id);
    }

    @Override
    public List<Reviews> sortReviewsByRating() {
        return reviewRepository.sortReviewsByRating();
    }

    @Override
    public List<Reviews> getReviewByFilmId(Integer filmId) {
        return reviewRepository.getReviewByFilmId(filmId);
    }
}
