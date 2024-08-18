package ra.nhom1_watchingfilmonline.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.nhom1_watchingfilmonline.model.entity.Categories;
import ra.nhom1_watchingfilmonline.model.entity.Comments;
import ra.nhom1_watchingfilmonline.service.ICommentService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private ICommentService commentService;

    @RequestMapping(value = "/loadComment")
    public String loadComment(@RequestParam(value = "size", defaultValue = "5") int size,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {

        List<Comments> comments = commentService.getAllComments(page, size);
        int totalComment = commentService.countComment();
        int totalPages = (int) Math.ceil((double) totalComment / size);

        model.addAttribute("comment", comments);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", size);

        return "admin/comment/listComment";
    }


    @RequestMapping(value = "/initEditComment/{id}", method = RequestMethod.GET)
    public String initEditComment(@PathVariable Integer id, Model model) {
        Comments comment = commentService.getCommentById(id);
        if (comment == null) {
            model.addAttribute("errorMessage", "Comment không tồn tại.");
            return "admin/comment/listComment";
        }

        model.addAttribute("comment", comment);
        return "admin/comment/editComment"; // Trả về trang chỉnh sửa bình luận
    }


    @RequestMapping(value = "/editComment/{id}", method = RequestMethod.POST)
    public String editComment(@ModelAttribute("comment") Comments comments,
                              @PathVariable Integer id, Model model, HttpSession session) {

        try {
            // Lấy bình luận cần cập nhật
            Comments existingComment = commentService.getCommentById(id);

            if (existingComment == null) {
                model.addAttribute("errorMessage", "Comment không tồn tại.");
                return "admin/comment/listComment";
            }else {
                // Cập nhật các thuộc tính bình luận
                existingComment.setContent(comments.getContent());

                // Lưu bình luận đã cập nhật
                commentService.updateComment(existingComment);

                return "redirect:/loadComment"; //

            }

        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Lỗi khi cập nhật comment: " + ex.getMessage());
            return "admin/comment/listComment";
        }
    }
    @RequestMapping(value = "/deleteComment/{id}")
    public String deleteComment(@PathVariable Integer id, Model model,HttpSession session) {
        String currentPage = (String) session.getAttribute("currentPage");
        try {
            Comments existingComment = commentService.getCommentById(id);
            if (existingComment == null) {
                model.addAttribute("error","Bình luận hiện không tôn tai");
                return "redirect:/loadComment";
            }else {
                commentService.deleteComment(id);
                return redirectBasedOnPage(currentPage);
            }

        }catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("error","loi khi xoa binh luon");
            return "admin/comment/listComment";
        }

    }

    @RequestMapping(value = "/searchCommentByFilmName", method = RequestMethod.GET)
    public String searchCommentByFilmName(@RequestParam("filmName") String filmName,
                                          @RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "size", defaultValue = "5") int size,
                                          Model model) {
        try {
            int offset = (page - 1) * size;
            List<Comments> commentsList = commentService.searchCommentsByFilm(filmName, offset, size);
            int totalComments = commentService.countCommentByFilmName(filmName);
            int totalPages = (int) Math.ceil((double) totalComments / size);

            model.addAttribute("comment", commentsList);
            model.addAttribute("filmName", filmName);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("size", size);

            if (commentsList.isEmpty()) {
                model.addAttribute("errorSearch", "Không tìm thấy bình luận nào với tên '" + filmName + "'.");
            }
            return "admin/comment/listComment";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Lỗi khi tìm kiếm: " + e.getMessage());
            return "admin/comment/listComment";
        }
    }

    @RequestMapping(value = "/sortCommentsByContent", method = RequestMethod.GET)
    public String sortCommentsByContent(@RequestParam(value = "sort", required = false) String sortOption,
                                        @RequestParam("content") String content,
                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size,
                                        Model model) {
        List<Comments> sortedComment;

        int offset = (page - 1) * size;

        // Sử dụng phương thức đúng để sắp xếp theo nội dung bình luận
        if ("1".equals(sortOption)) {
            sortedComment = commentService.sortCommentsByContent(content, offset, size);
        } else {
            sortedComment = commentService.getAllComments(page, size);
        }

        // Đếm tổng số bình luận dựa trên nội dung
        int totalComment = commentService.countCommentByContent(content);
        int totalPages = (totalComment + size - 1) / size;

        model.addAttribute("comment", sortedComment);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);

        return "admin/comment/listComment";
    }



    // phuong thuc tra ve duong dan
    private String redirectBasedOnPage(String currentPage) {
        if (currentPage == null || currentPage.isEmpty()) {
            return "redirect:/home";
        }
        return "redirect:" + currentPage;
    }
}
