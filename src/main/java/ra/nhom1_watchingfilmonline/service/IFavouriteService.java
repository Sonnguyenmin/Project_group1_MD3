package ra.nhom1_watchingfilmonline.service;

import ra.nhom1_watchingfilmonline.model.entity.Favourite;
import ra.nhom1_watchingfilmonline.model.entity.Reviews;

import java.util.List;

public interface IFavouriteService {
    List<Favourite> getAllFavourites();
    Boolean addFavourite(Favourite favourite);
    Boolean removeFavourite(Favourite favourite);
    Favourite getFavouriteById(Integer id);
    List<Favourite> getFavouriteByFilmId(Integer filmId);
}
