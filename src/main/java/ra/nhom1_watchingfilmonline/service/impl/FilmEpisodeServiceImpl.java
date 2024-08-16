package ra.nhom1_watchingfilmonline.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.nhom1_watchingfilmonline.model.dto.request.FilmEpisodeRequest;
import ra.nhom1_watchingfilmonline.model.entity.FilmEpisode;
import ra.nhom1_watchingfilmonline.repository.impl.FilmEpisodeRepositoryImpl;
import ra.nhom1_watchingfilmonline.service.FilmEpisodeService;
import ra.nhom1_watchingfilmonline.service.UploadService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmEpisodeServiceImpl implements FilmEpisodeService {


    @Autowired
    private UploadService uploadService;

    @Autowired
    private FilmEpisodeRepositoryImpl filmEpisodeRepository;

    @Override
    public List<FilmEpisode> findAll() {
        return filmEpisodeRepository.findAll();
    }

    @Override
    public FilmEpisode findById(Integer filmEpisodeId) {
        return filmEpisodeRepository.findById(filmEpisodeId);
    }

    @Override
    public void save(FilmEpisodeRequest filmEpisodeRequest) {
        FilmEpisode filmEpisode = FilmEpisode.builder()
                .filmEpisodeId(filmEpisodeRequest.getFilmEpisodeId())
                .films(filmEpisodeRequest.getFilms())
                .episodeNumber(filmEpisodeRequest.getEpisodeNumber())
                .filmEpisodeUrl(filmEpisodeRequest.getFilmEpisodeUrl())
                .build();
        if(filmEpisodeRequest.getFilmEpisodeId() == null) {
            filmEpisode.setFilmEpisodeImage(uploadService.uploadFileToServer(filmEpisodeRequest.getFilmEpisodeImage()));
        }
        else {
            if (filmEpisodeRequest.getFilmEpisodeImage() != null && filmEpisodeRequest.getFilmEpisodeImage().getSize() > 0){
                filmEpisode.setFilmEpisodeImage(uploadService.uploadFileToServer(filmEpisodeRequest.getFilmEpisodeImage()));
            }
            else {
                filmEpisode.setFilmEpisodeImage(filmEpisodeRepository.getImageById(filmEpisodeRequest.getFilmEpisodeId()));
            }
        }
        filmEpisodeRepository.save(filmEpisode);
    }

    @Override
    public Boolean delete(Integer filmEpisodeId) {
        return filmEpisodeRepository.delete(filmEpisodeId);
    }
}
