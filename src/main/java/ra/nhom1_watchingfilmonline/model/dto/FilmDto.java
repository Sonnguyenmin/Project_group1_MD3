package ra.nhom1_watchingfilmonline.model.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FilmDto {
    private Integer filmId;
    private String filmName;
    private List<String> categoryNames;
}
