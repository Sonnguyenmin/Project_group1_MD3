package ra.nhom1_watchingfilmonline.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ra.nhom1_watchingfilmonline.model.entity.Categories;
import ra.nhom1_watchingfilmonline.service.ICategoriesService;


import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private ICategoriesService categoryService;


    @RequestMapping(value = "/loadCategory")
    public String loadCategory(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "5") int size,Model model) {
        List<Categories> filmCategoryList = categoryService.getAll(page, size);
        int totalCategories = categoryService.countCategories();
        int totalPages = (int) Math.ceil((double) totalCategories / size);
        model.addAttribute("category", filmCategoryList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "admin/category/listCategory";
    }

    @RequestMapping(value = "/initAddCategory")
    public String initAddCategory(Model model) {
        Categories newFilmCategory = new Categories();
        model.addAttribute("category", newFilmCategory);
        return "admin/category/addCategory";
    }
    @RequestMapping(value = "/addCategory")
    public String addCategory(@Valid @ModelAttribute("category") Categories filmCategory,
                              BindingResult result , Model model) {
        if (result.hasErrors()) {
            return "admin/category/addCategory";
        }
        try {
            // Kiểm tra xem danh mục đã tồn tại chưa
            Categories existingCategory = categoryService.findCategoryByName(filmCategory.getCategoryName());
            if (existingCategory != null) {
                model.addAttribute("errorMessage", "Danh mục với tên '" + filmCategory.getCategoryName() + "' đã tồn tại.");
                return "admin/category/addCategory";
            }
            // Lưu category vào cơ sở dữ liệu
            categoryService.save(filmCategory);
            // Chuyển hướng đến trang danh sách category hoặc trang thành công
            return "redirect:/loadCategory";
        }catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi (ví dụ: lưu vào cơ sở dữ liệu không thành công)
            model.addAttribute("errorMessage", "Lỗi khi lưu danh mục: " + e.getMessage());
            return "admin/category/addCategory";
        }
    }

    @RequestMapping(value = "/initEditCategory/{id}", method = RequestMethod.GET)
    public String initEditCategory(@PathVariable("id") Integer id, Model model) {
        // Tìm danh mục theo ID
        Categories updateCategory = categoryService.findById(id);

        if (updateCategory == null) {
            model.addAttribute("errorMessage", "Danh mục không tồn tại.");
            return "redirect:/loadCategory"; // Chuyển hướng đến danh sách danh mục nếu không tìm thấy
        }

        model.addAttribute("category", updateCategory);
        return "admin/category/editCategory";
    }

    @RequestMapping(value = "/editCategory", method = RequestMethod.POST)
    public String editCategory(@Valid @ModelAttribute("category") Categories filmCategory,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/category/editCategory";
        }

        try {
            // Tìm danh mục theo ID
            Categories existingCategory = categoryService.findById(filmCategory.getCategoryId());

            if (existingCategory == null) {
                model.addAttribute("errorMessage", "Danh mục không tồn tại.");
                return "redirect:/loadCategory"; // Chuyển hướng đến danh sách danh mục nếu không tìm thấy
            }

            // Cập nhật thông tin danh mục
            existingCategory.setCategoryName(filmCategory.getCategoryName());
            existingCategory.setDescription(filmCategory.getDescription());
            existingCategory.setStatus(filmCategory.getStatus());

            // Kiểm tra xem danh mục đã tồn tại chưa
            Categories categoryByName = categoryService.findCategoryByName(filmCategory.getCategoryName());
            if (categoryByName != null && !categoryByName.getCategoryId().equals(existingCategory.getCategoryId())) {
                model.addAttribute("errorMessage", "Danh mục với tên '" + filmCategory.getCategoryName() + "' đã tồn tại.");
                return "admin/category/editCategory";
            }

            // Lưu thông tin danh mục đã cập nhật vào cơ sở dữ liệu
            categoryService.update(existingCategory);

            return "redirect:/loadCategory"; // Chuyển hướng đến danh sách danh mục

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
            return "admin/category/editCategory";
        }
    }


    @RequestMapping(value = {"deleteCategory/{id}"})
    public String deleteCategory(@PathVariable Integer id, Model model) {
        try {
            // Lấy category cần xóa
            Categories filmCategory = categoryService.findById(id);
            if (filmCategory == null) {
                model.addAttribute("error", "category không tồn tại.");
                return "redirect:/loadCategory";
            }else {
                categoryService.delete(id);
                return "redirect:/loadCategory";
            }

        }catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Lỗi khi xóa sản phẩm: " + e.getMessage());
            return "redirect:/loadCategory";
        }

    }

    @RequestMapping(value = "/searchCategoryByName", method = RequestMethod.GET)
    public String searchCategoryByName(@RequestParam("categoryName") String categoryName,
                                       @RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "size", defaultValue = "5") int size,
                                       Model model) {
        try {
            // Tính toán offset dựa trên trang hiện tại và kích thước trang
            int offset = (page - 1) * size;
            // Tìm kiếm category theo tên với phân trang
            List<Categories> filmCategoryList = categoryService.searchByCategoryName(categoryName, offset, size);

            // Tính tổng số kết quả phù hợp với tên tìm kiếm
            int totalCount = categoryService.countCategoriesByName(categoryName);
            int totalPages = (totalCount + size - 1) / size;

            model.addAttribute("categoryName", categoryName); // Giữ giá trị tìm kiếm
            model.addAttribute("category", filmCategoryList);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages); // Số trang tổng
            model.addAttribute("pageSize", size);

            if (filmCategoryList.isEmpty()) {
                model.addAttribute("errorSearch", "Không tìm thấy category nào với tên '" + categoryName + "'.");
            }
            return "admin/category/listCategory";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Lỗi khi tìm kiếm sản phẩm: " + e.getMessage());
            return "admin/category/listCategory"; // Trang hiển thị danh sách sản phẩm
        }
    }


    @RequestMapping(value = "/sortByCategoryName", method = RequestMethod.GET)
    public String sortByCategoryName(@RequestParam(value = "sort", required = false) String sortOption,
                                     @RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "size", defaultValue = "5") int size,
                                     Model model) {
        List<Categories> sortedCategories;

        int offset = (page - 1) * size;

        if ("1".equals(sortOption)) {
            sortedCategories = categoryService.sortByCategoryName(offset, size);
        } else {
            sortedCategories = categoryService.getAll(page, size);
        }

        int totalCategories = categoryService.countCategories();
        int totalPages = (totalCategories + size - 1) / size;

        model.addAttribute("category", sortedCategories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);

        return "admin/category/listCategory";
    }



}
