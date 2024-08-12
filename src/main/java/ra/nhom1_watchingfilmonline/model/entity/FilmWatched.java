package ra.nhom1_watchingfilmonline.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FilmWatched {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer filmWatchedId;

    private Date watchedDate;


    @ManyToOne
    @JoinColumn(name = "filmId")
    private Films films;

    @ManyToOne
    @JoinColumn(name = "uaerId")
    private Users users;
}
