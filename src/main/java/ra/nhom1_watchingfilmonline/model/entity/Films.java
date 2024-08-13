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

    @Column(name = "filmName" ,length = 100, nullable = false, unique = true)
    private String filmName;

    private String description;

    private String image;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @Column(name = "director", length = 70)
    private String director;

    @Column(name = "actorName", length = 70)
    private String actorName;

    @Column(name = "language", length = 30)
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
