package ra.nhom1_watchingfilmonline.model.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class BannerRequest {
    private Integer bannerId;

    private String bannerName;

    private MultipartFile multipartFile;

    private String description;
}
