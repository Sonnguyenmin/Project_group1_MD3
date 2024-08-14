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
    public String loadCategory(Model model) {
        List<Categories> filmCategoryList = categoryService.findAll();
        System.out.println("Danh sách danh mục: " + filmCategoryList); // Ghi log
        model.addAttribute("category", filmCategoryList);
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
    public String searchCategoryByName(@RequestParam("categoryName") String categoryName, Model model) {
        try {
            // Tìm kiếm category theo tên
            List<Categories> filmCategoryList = categoryService.searchByCategoryName(categoryName);
            model.addAttribute("categoryName", categoryName); // Giữ giá trị tìm kiếm
            if (!filmCategoryList.isEmpty()) {
                // Thêm danh sách category vào model để hiển thị trên view
                model.addAttribute("category", filmCategoryList);
                return "admin/category/listCategory";
            }else {
//                Nếu không có category nào tìm thấy, thêm thông báo lỗi vào model
                model.addAttribute("errorSearch","Không tìm thấy category nào với tên '" + categoryName + "'.");
                return "admin/category/listCategory";
            }

        }catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Lỗi khi tìm kiếm sản phẩm: " + e.getMessage());
            return "admin/category/listCategory"; // Trang hiển thị danh sách sản phẩm
        }
    }

    @RequestMapping(value = "/categoryStatusChange/{id}", method = RequestMethod.POST)
    public String categoryStatusChange(@PathVariable("id") Integer id,
                                       @RequestParam("status") Boolean status,
                                       Model model) {
        try {
            // Tìm danh mục theo ID
            Categories existingCategory = categoryService.findById(id);

            if (existingCategory == null) {
                // Nếu danh mục không tồn tại, thêm thông báo lỗi vào model và chuyển hướng về danh sách danh mục
                model.addAttribute("errorMessage", "Danh mục không tồn tại.");
                return "redirect:/loadCategory";
            } else {
                // Cập nhật trạng thái của danh mục
                existingCategory.setStatus(!status); // Toggle trạng thái

                // Lưu danh mục với trạng thái đã cập nhật vào cơ sở dữ liệu
                categoryService.update(existingCategory);

                // Chuyển hướng đến danh sách danh mục hoặc trang thành công
                return "redirect:/loadCategory";
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi (ví dụ: lưu vào cơ sở dữ liệu không thành công)
            model.addAttribute("errorMessage", "Lỗi khi cập nhật trạng thái danh mục: " + e.getMessage());
            return "redirect:/loadCategory";
        }
    }


    @RequestMapping(value = "/sortByCategoryName", method = RequestMethod.GET)
    public String sortByCategoryName(@RequestParam(value = "sort", required = false) String sortOption,
                                     Model model) {
        List<Categories> sortedCategories;

        if ("1".equals(sortOption)) {
            // Sắp xếp theo tên danh mục
            sortedCategories = categoryService.sortByCategoryName(); //
        } else {
            // Trở về danh sách ban đầu hoặc không sắp xếp
            sortedCategories = categoryService.findAll(); // Phương thức lấy tất cả danh mục
        }
        model.addAttribute("category", sortedCategories);
        return "admin/category/listCategory";
    }



}
