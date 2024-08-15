package ra.nhom1_watchingfilmonline.model.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FilmEpisodeRequest {
    private Integer filmEpisodeId;

    private Integer episodeNumber;

    private String filmEpisodeUrl;

    private MultipartFile filmEpisodeImage;
}
