package ra.nhom1_watchingfilmonline.repository;

import ra.nhom1_watchingfilmonline.model.entity.Favourite;

import java.util.List;

public interface IFavouriteRepository {
    List<Favourite> getAllFavourites();
    Boolean addFavourite(Favourite favourite);
    Boolean removeFavourite(Favourite favourite);
    Favourite getFavouriteById(Integer id);
    List<Favourite> getFavouriteByFilmId(Integer filmId);
}
