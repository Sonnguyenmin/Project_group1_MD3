package ra.nhom1_watchingfilmonline.repository;


import ra.nhom1_watchingfilmonline.model.entity.Films;

import java.util.List;

public interface FirmRepository {
    List<Films> findAll();
    Boolean addFilm(Films film);
    Boolean updateFilm(Films film);
    Boolean deleteFilm(Films film);
    Films getFilmById(Integer filmId);
    List<Films> searchFilm(String filmName);
}
