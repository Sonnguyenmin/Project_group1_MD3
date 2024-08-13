package ra.nhom1_watchingfilmonline.model.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FilmEpisode {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer filmEpisodeId;

    private Integer episodeNumber;

    private String filmEpisodeUrl;

    private String filmEpisodeImage;

    @ManyToOne
    @JoinColumn (name = "filmId")
    private Films films;
}
