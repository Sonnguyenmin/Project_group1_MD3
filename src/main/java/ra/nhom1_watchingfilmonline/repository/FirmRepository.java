package ra.nhom1_watchingfilmonline.repository;


import ra.nhom1_watchingfilmonline.model.entity.Films;

import java.util.List;

public interface FirmRepository {
//    Integer offset, Integer size
    List<Films> findAll();
    void saveFilm(Films films);
    Boolean deleteFilm(Integer filmId);
    Films getFilmById(Integer filmId);
    List<Films> searchFilm(String filmName);
    String getImageById(Integer filmId);
//    Integer getTotalFilms();

    Boolean isFilmNameExists(String filmName);
    Films findFilmByName(String filmName);
    List<Films> sortByFilmName();
}
