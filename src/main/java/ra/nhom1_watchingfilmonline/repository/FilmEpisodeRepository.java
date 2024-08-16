package ra.nhom1_watchingfilmonline.repository;

import ra.nhom1_watchingfilmonline.model.entity.FilmEpisode;

import java.util.List;

public interface FilmEpisodeRepository {
    List<FilmEpisode> findAll();
    FilmEpisode findById(Integer filmEpisodeId);
    void save(FilmEpisode filmEpisode);
    Boolean delete(Integer filmEpisodeId);
    String getImageById(Integer filmEpisodeId);
}
