package ra.nhom1_watchingfilmonline.repository;


import ra.nhom1_watchingfilmonline.model.entity.Categories;

import java.util.List;

public interface ICategoriesRepository {
    List<Categories> findAll();
    List<Categories> getAll(int page, int size);
    Boolean save(Categories categories);
    Categories findById(Integer id);
    Boolean update(Categories filmCategory);
    Boolean delete(Integer id);
    List<Categories> searchByCategoryName(String categoryName, int offset, int size);
    Boolean isCategoryNameExists(String categoryName);
    Categories findCategoryByName(String categoryName);
    List<Categories> sortByCategoryName(int offset, int size);
    int countCategories();
    int countCategoriesByName(String categoryName);
}
