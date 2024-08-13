package ra.nhom1_watchingfilmonline.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BannerRequest {
    private Integer bannerId;

    private String bannerName;

    private MultipartFile multipartFile;

    private String description;
}
