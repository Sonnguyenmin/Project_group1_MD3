package ra.nhom1_watchingfilmonline.repository.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ra.nhom1_watchingfilmonline.model.entity.Films;
import ra.nhom1_watchingfilmonline.repository.FirmRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FilmRepositoryImpl implements FirmRepository {
    @Override
    public List<Films> findAll() {
        return List.of();
    }

    @Override
    public Boolean addFilm(Films film) {
        return null;
    }

    @Override
    public Boolean updateFilm(Films film) {
        return null;
    }

    @Override
    public Boolean deleteFilm(Films film) {
        return null;
    }

    @Override
    public Films getFilmById(Integer filmId) {
        return null;
    }

    @Override
    public List<Films> searchFilm(String filmName) {
        return List.of();
    }
}
