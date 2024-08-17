package ra.nhom1_watchingfilmonline.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.nhom1_watchingfilmonline.model.entity.Favourite;
import ra.nhom1_watchingfilmonline.repository.IFavouriteRepository;
import ra.nhom1_watchingfilmonline.service.IFavouriteService;

import java.util.List;
@Service
public class FavouriteServiceImpl implements IFavouriteService {
    @Autowired
    private IFavouriteRepository favouriteRepository;
    @Override
    public List<Favourite> getAllFavourites() {
        return favouriteRepository.getAllFavourites();
    }

    @Override
    public Boolean addFavourite(Favourite favourite) {
        return favouriteRepository.addFavourite(favourite);
    }

    @Override
    public Boolean removeFavourite(Favourite favourite) {
        return favouriteRepository.removeFavourite(favourite);
    }

    @Override
    public Favourite getFavouriteById(Integer id) {
        return favouriteRepository.getFavouriteById(id);
    }

    @Override
    public List<Favourite> getFavouriteByFilmId(Integer filmId) {
        return favouriteRepository.getFavouriteByFilmId(filmId);
    }

    @Override
    public List<Favourite> findByUser_UserId(Integer userId) {
        return favouriteRepository.findByUser_UserId(userId);
    }

    @Override
    public boolean isFavouriteExists(Integer filmId, Integer userId) {
        return favouriteRepository.isFavouriteExists(filmId, userId);
    }
}
