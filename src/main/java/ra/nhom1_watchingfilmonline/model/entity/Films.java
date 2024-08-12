package ra.nhom1_watchingfilmonline.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Films {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer filmId;

    private String filmName;

    private String description;

    private String image;

    private Date releaseDate;

    private String director;

    private String actorName;

    private String language;

    private Integer totalFilm;

    private Boolean seriesSingle;

    private Integer totalEpisode;

    private Boolean isFree;

    private Integer status;

    @ManyToOne
    @JoinColumn (name = "countryId")
    private Countries countries;

    @ManyToMany
    @JoinTable(name ="film_Category",
    joinColumns = @JoinColumn (name = "filmId"),
            inverseJoinColumns = @JoinColumn (name = "categoryId")
    )
    private List<Categories> categories;




}
