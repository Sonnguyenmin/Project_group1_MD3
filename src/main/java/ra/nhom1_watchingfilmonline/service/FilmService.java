package ra.nhom1_watchingfilmonline.service;

import ra.nhom1_watchingfilmonline.model.dto.request.FilmRequest;
import ra.nhom1_watchingfilmonline.model.entity.Categories;
import ra.nhom1_watchingfilmonline.model.entity.Films;

import java.util.List;
import java.util.Map;

public interface FilmService {
    List<Films> findAll(int page, int size, String search);

    Long totalAllFilm(String search);

    List<Films> findAllByOrderByFilmAsc(int page, int size);


    List<Films> findAllByOrderByFilmDesc(int page, int size);

    void saveFilm(FilmRequest filmRequest);

    Boolean deleteFilm(Integer filmId);

    Films getFilmById(Integer filmId);

    Boolean isFilmNameExists(String filmName);


 
    List<Films> getFilmFindAll();
    
    Films findFilmByName(String filmName);
    List<Films> getAllFilms();

     Films findByIdWithCategories(Integer filmId);

    List<Films> findAllPhimBo();
    List<Films> findAllPhimLe();
// chi lam phim de xuat
    List<Films> getTop5RecommendedFilms();

    List<Films> upcomingMovies();
    List<Films> findFilmsByCategory(Integer categoryId);

    List<Films> findAllUserFilm(int page, int size, String search);
    Long totalAllUFilm(String search);
    List<Films> findAllByOrderByUFilmNameAsc(int page, int size);
    List<Films> findAllByOrderByUFilmNameDesc(int page, int size);

}
