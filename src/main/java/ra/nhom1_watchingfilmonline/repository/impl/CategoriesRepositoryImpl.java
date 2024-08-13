package ra.nhom1_watchingfilmonline.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.nhom1_watchingfilmonline.model.entity.Categories;
import ra.nhom1_watchingfilmonline.repository.ICategoriesRepository;


import java.util.List;


@Repository
public class CategoriesRepositoryImpl implements ICategoriesRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Categories> findAll() {
        Session session = sessionFactory.openSession();
        List<Categories> filmCategories = null;
        try {
            session.beginTransaction();
            filmCategories = session.createQuery("from Categories").list();
            return filmCategories;
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Boolean save(Categories filmCategory) {
        if (isCategoryNameExists(filmCategory.getCategoryName())) {
            throw new RuntimeException("Category name already exists.");
        }
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(filmCategory);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return false;
    }

    @Override
    public Categories findById(Integer id) {
        Session session = sessionFactory.openSession();
        Categories filmCategory = null;
        try {
            session.beginTransaction();
            filmCategory = session.get(Categories.class, id);
            session.getTransaction().commit();
            return filmCategory;
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Boolean update(Categories categories) {
        if (categories == null) {
            throw new IllegalArgumentException("Danh mục không được null");
        }

        if (isCategoryNameExists(categories.getCategoryName())) {
            Categories existingCategory = findCategoryByName(categories.getCategoryName());
            if (existingCategory != null && !existingCategory.getCategoryId().equals(categories.getCategoryId())) {
                throw new RuntimeException("Tên danh mục đã tồn tại.");
            }
        }

        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(categories);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }


    @Override
    public Boolean delete(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Categories filmCategory = findById(id);
            if (filmCategory != null) {
                session.delete(filmCategory);
                session.getTransaction().commit();
                return true;
            }else {
                return false;// Không tìm thấy đối tượng để xóa
            }
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return false;
    }

    @Override
    public List<Categories> searchByCategoryName(String categoryName) {
        Session session = sessionFactory.openSession();
        List<Categories> filmCategories = null;
        try {
            session.beginTransaction();
            // Sử dụng LIKE để tìm kiếm theo tên thể loại
            filmCategories = session.createQuery("from Categories where categoryName like :categoryName", Categories.class)
                    .setParameter("categoryName", "%" + categoryName + "%")
                    .list();
            session.getTransaction().commit();
            return filmCategories;
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Boolean isCategoryNameExists(String categoryName) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            String hql = "select count(*) from Categories where categoryName = :categoryName";
            Long count = (Long) session.createQuery(hql)
                    .setParameter("categoryName", categoryName)
                    .uniqueResult();
            session.getTransaction().commit();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Categories findCategoryByName(String categoryName) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            String hql = "from Categories where categoryName = :categoryName";
            return (Categories) session.createQuery(hql)
                    .setParameter("categoryName", categoryName)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Categories> sortByCategoryName() {
        Session session = sessionFactory.openSession();
        List<Categories> sortCategories = null;
        try {
            session.beginTransaction();
            String hql = "from Categories order by categoryName";
            Query<Categories> query = session.createQuery(hql, Categories.class);
            sortCategories = query.getResultList();
            session.getTransaction().commit();
            return sortCategories;
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }

        return sortCategories;
    }
}
