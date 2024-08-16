package ra.nhom1_watchingfilmonline.repository;


import ra.nhom1_watchingfilmonline.model.entity.Films;

import java.util.List;

public interface FirmRepository {
    List<Films> findAll(int page, int size, String search);
    Long totalAllFilm(String search);
    List<Films> findAllByOrderByFilmNameAsc(int page, int size);
    List<Films> findAllByOrderByFilmNameDesc(int page, int size);
    void saveFilm(Films films);
    Boolean deleteFilm(Integer filmId);
    Films getFilmById(Integer filmId);
    String getImageById(Integer filmId);
    Boolean isFilmNameExists(String filmName);
    Films findFilmByName(String filmName);


    List<Films> getAllFilms();
    Films findByIdWithCategories(Integer filmId);


    List<Films> findAllPhimBo();
    List<Films> findAllPhimLe();

//    FilmDto getFilmDTO(Integer filmId);



}
