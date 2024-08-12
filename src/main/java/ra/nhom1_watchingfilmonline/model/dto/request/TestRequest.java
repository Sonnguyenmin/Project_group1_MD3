package ra.nhom1_watchingfilmonline.model.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TestRequest {
    private Integer id;
    private String name;
    private MultipartFile multipartFile;
}
