package ra.nhom1_watchingfilmonline.service;

import ra.nhom1_watchingfilmonline.model.dto.request.FilmRequest;
import ra.nhom1_watchingfilmonline.model.entity.Categories;
import ra.nhom1_watchingfilmonline.model.entity.Films;

import java.util.List;

public interface FilmService {
//    Integer page,Integer size
    List<Films> findAll();
    void saveFilm(FilmRequest filmRequest);
    Boolean deleteFilm(Integer filmId);
    Films getFilmById(Integer filmId);
    List<Films> searchFilm(String filmName);
//    Integer getTotalFilms();

    Boolean isFilmNameExists(String filmName);
    Films findFilmByName(String filmName);
    List<Films> sortByFilmName();
}
