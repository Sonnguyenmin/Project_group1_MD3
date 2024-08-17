package ra.nhom1_watchingfilmonline.service;

import ra.nhom1_watchingfilmonline.model.dto.request.FilmEpisodeRequest;
import ra.nhom1_watchingfilmonline.model.dto.request.FilmRequest;
import ra.nhom1_watchingfilmonline.model.entity.FilmEpisode;

import java.util.List;

public interface FilmEpisodeService {
    List<FilmEpisode> findAll();
    FilmEpisode findById(Integer filmEpisodeId);
    void save(FilmEpisodeRequest filmEpisodeRequest);
    Boolean delete(Integer filmEpisodeId);
}
