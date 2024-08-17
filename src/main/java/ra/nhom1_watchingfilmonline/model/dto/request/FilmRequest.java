package ra.nhom1_watchingfilmonline.model.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import ra.nhom1_watchingfilmonline.model.entity.Countries;
import ra.nhom1_watchingfilmonline.model.entity.FilmEpisode;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FilmRequest {
    private Integer filmId;

    @NotEmpty (message = "Tên phim không được để trống")
    private String filmName;

    private String description;

    private MultipartFile image;

    @NotNull(message = "Vui lòng chọn ngày phim ra mắt")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;


    private String director;


    private String actorName;


    private String language;

    private Integer totalTime;


    private Boolean seriesSingle;

    @Min(value = 1, message = "Số tập phim ít nhất là 1")
    private Integer totalEpisode;

    private Boolean isFree;

    @Range(min = 1, max = 3)
    private Integer status;

    private Countries countries;

    private List<Integer> categories;

    private FilmEpisode filmEpisode;
}
