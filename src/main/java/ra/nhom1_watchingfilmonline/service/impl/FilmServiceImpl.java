package ra.nhom1_watchingfilmonline.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.nhom1_watchingfilmonline.model.dto.FilmDto;
import ra.nhom1_watchingfilmonline.model.dto.request.FilmRequest;
import ra.nhom1_watchingfilmonline.model.entity.Categories;
import ra.nhom1_watchingfilmonline.model.entity.Films;
import ra.nhom1_watchingfilmonline.repository.impl.CategoriesRepositoryImpl;
import ra.nhom1_watchingfilmonline.repository.impl.FilmRepositoryImpl;
import ra.nhom1_watchingfilmonline.service.FilmService;
import ra.nhom1_watchingfilmonline.service.UploadService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    @Autowired
    private FilmRepositoryImpl filmRepository;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private CategoriesRepositoryImpl categoriesRepository;

    @Override
    public List<Films> findAll(int page, int size, String search) {
        return filmRepository.findAll(page, size, search);
    }


    @Override
    public void saveFilm(FilmRequest filmRequest) {

        List<Categories> categoriesList = filmRequest.getCategories().stream().map(item -> categoriesRepository.findById(item)).collect(Collectors.toList());

        Films films = Films.builder()
                .filmId(filmRequest.getFilmId())
                .filmName(filmRequest.getFilmName())
                .description(filmRequest.getDescription())
                .actorName(filmRequest.getActorName())
                .countries(filmRequest.getCountries())
                .releaseDate(filmRequest.getReleaseDate())
                .totalTime(filmRequest.getTotalTime())
                .categories(categoriesList)
                .director(filmRequest.getDirector())
                .language(filmRequest.getLanguage())
                .totalEpisode(filmRequest.getTotalEpisode())
                .seriesSingle(filmRequest.getSeriesSingle())
                .isFree(filmRequest.getIsFree())
                .status(filmRequest.getStatus())
                .build();
        if(filmRequest.getFilmId() == null) {
            films.setImage(uploadService.uploadFileToServer(filmRequest.getImage()));
        } else  {
            if (filmRequest.getImage() != null && filmRequest.getImage().getSize() > 0) {
                films.setImage(uploadService.uploadFileToServer(filmRequest.getImage()));
            }
            else {
                films.setImage(filmRepository.getImageById(filmRequest.getFilmId()));
            }
        }
        filmRepository.saveFilm(films);
    }

    @Override
    public Boolean deleteFilm(Integer filmId) {
        return filmRepository.deleteFilm(filmId);
    }

    @Override
    public Films getFilmById(Integer filmId) {
        return filmRepository.getFilmById(filmId);
    }

    @Override
    public Boolean isFilmNameExists(String filmName) {
        return filmRepository.isFilmNameExists(filmName);
    }

    @Override
    public Films findFilmByName(String filmName) {
        return filmRepository.findFilmByName(filmName);
    }

    @Override
    public List<Films> getFilmFindAll() {
        return filmRepository.getFilmFindAll();
    }

    @Override
    public Long totalAllFilm(String search) {
        return filmRepository.totalAllFilm(search);
    }

    @Override

    public List<Films> findAllByOrderByFilmAsc(int page, int size) {
        return filmRepository.findAllByOrderByFilmNameAsc(page, size);
    }

    @Override
    public List<Films> findAllByOrderByFilmDesc(int page, int size) {
        return filmRepository.findAllByOrderByFilmNameDesc(page, size);
    }

    public FilmDto getFilmDTO(Integer filmId) {
        return filmRepository.getFilmDTO(filmId);
    }

}
