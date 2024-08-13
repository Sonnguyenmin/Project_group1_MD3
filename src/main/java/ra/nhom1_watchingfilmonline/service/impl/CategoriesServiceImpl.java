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
    public List<Categories> searchByCategoryName(String categoryName) {
        return categoryRepository.searchByCategoryName(categoryName);
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
    public List<Categories> sortByCategoryName() {
        return categoryRepository.sortByCategoryName();
    }
}
