package ra.nhom1_watchingfilmonline.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(nullable = false , length = 255, unique = true)
    @NotBlank(message = "Tên danh mục không được để trống")
    private String categoryName;

    @Column(nullable = false,length = 255)
    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Trạng thái không được để trống")
    private Boolean status;

}
