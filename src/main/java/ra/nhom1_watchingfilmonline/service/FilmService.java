package ra.nhom1_watchingfilmonline.service;

import ra.nhom1_watchingfilmonline.model.dto.request.FilmRequest;
import ra.nhom1_watchingfilmonline.model.entity.Films;

import java.util.List;

public interface FilmService {
    List<Films> findAll(int page, int size, String search);
    Long totalAllFilm(String search);

    List<Films> findAllByOrderByFilmAsc(int page, int size);
    // Sắp xếp theo username giảm dần
    List<Films> findAllByOrderByFilmDesc(int page, int size);
    void saveFilm(FilmRequest filmRequest);
    Boolean deleteFilm(Integer filmId);
    Films getFilmById(Integer filmId);
    Boolean isFilmNameExists(String filmName);
    Films findFilmByName(String filmName);
    List<Films> getAllFilms();

     Films findByIdWithCategories(Integer filmId);


    List<Films> sortByFilmName();

    List<Films> findAllPhimBo();
    List<Films> findAllPhimLe();

    FilmDto getFilmDTO(Integer filmId);



}
