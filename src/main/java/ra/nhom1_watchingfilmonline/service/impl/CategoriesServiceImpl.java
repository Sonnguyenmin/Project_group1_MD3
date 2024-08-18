package ra.nhom1_watchingfilmonline.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.nhom1_watchingfilmonline.model.entity.Categories;
import ra.nhom1_watchingfilmonline.repository.ICategoriesRepository;
import ra.nhom1_watchingfilmonline.service.ICategoriesService;


import java.util.List;
@Service
public class CategoriesServiceImpl implements ICategoriesService {
    @Autowired
    private ICategoriesRepository categoryRepository;

    @Override
    public List<Categories> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Categories> getAll(int page, int size) {
        return categoryRepository.getAll(page,size);
    }

    @Override
    public Boolean save(Categories filmCategory) {
        return categoryRepository.save(filmCategory);
    }

    @Override
    public Categories findById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Boolean update(Categories filmCategory) {
        return categoryRepository.update(filmCategory);
    }

    @Override
    public Boolean delete(Integer id) {
        return categoryRepository.delete(id);
    }

    @Override
    public List<Categories> searchByCategoryName(String categoryName, int offset, int size) {
        return categoryRepository.searchByCategoryName(categoryName, offset, size);
    }

    @Override
    public Boolean isCategoryNameExists(String categoryName) {
        return categoryRepository.isCategoryNameExists(categoryName);
    }

    @Override
    public Categories findCategoryByName(String categoryName) {
        return categoryRepository.findCategoryByName(categoryName);
    }

    @Override
    public List<Categories> sortByCategoryName(int offset, int size) {

        return categoryRepository.sortByCategoryName(offset, size);
    }

    @Override
    public int countCategories() {
        return categoryRepository.countCategories();
    }

    @Override
    public int countCategoriesByName(String categoryName) {
        return categoryRepository.countCategoriesByName(categoryName);
    }
}
