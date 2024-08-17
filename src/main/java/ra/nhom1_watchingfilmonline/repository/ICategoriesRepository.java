package ra.nhom1_watchingfilmonline.repository;


import ra.nhom1_watchingfilmonline.model.entity.Categories;

import java.util.List;

public interface ICategoriesRepository {
    List<Categories> findAll();
    Boolean save(Categories categories);
    Categories findById(Integer id);
    Boolean update(Categories filmCategory);
    Boolean delete(Integer id);
    List<Categories> searchByCategoryName(String categoryName);
    Boolean isCategoryNameExists(String categoryName);
    Categories findCategoryByName(String categoryName);
    List<Categories> sortByCategoryName();
}
