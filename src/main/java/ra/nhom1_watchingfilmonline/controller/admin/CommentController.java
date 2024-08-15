package ra.nhom1_watchingfilmonline.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ra.nhom1_watchingfilmonline.model.entity.Categories;
import ra.nhom1_watchingfilmonline.model.entity.Comments;
import ra.nhom1_watchingfilmonline.service.ICommentService;

import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private ICommentService commentService;

    @RequestMapping(value = "/loadComment")
    public String loadComment(Model model) {

        List<Comments> comments = commentService.getAllComments();
        System.out.println("Danh sách danh mục: " + comments); // Ghi log check
        model.addAttribute("comment", comments);
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
                              @PathVariable Integer id, Model model) {
        try {
            // Lấy bình luận cần cập nhật
            Comments existingComment = commentService.getCommentById(id);

            if (existingComment == null) {
                model.addAttribute("errorMessage", "Comment không tồn tại.");
                return "admin/comment/listComment";
            }

            // Cập nhật các thuộc tính bình luận
            existingComment.setContent(comments.getContent());

            // Lưu bình luận đã cập nhật
            commentService.addComment(existingComment);

            return "redirect:/loadComment"; // Hoặc trang chi tiết bình luận nếu cần
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Lỗi khi cập nhật comment: " + ex.getMessage());
            return "admin/comment/listComment";
        }
    }




}
