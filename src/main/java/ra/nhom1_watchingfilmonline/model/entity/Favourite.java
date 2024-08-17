package ra.nhom1_watchingfilmonline.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Favourite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer favouriteId;
    @ManyToOne
    @JoinColumn (name ="userId")
    private Users users;

    @ManyToOne
    @JoinColumn (name = "filmId")
    private Films films;

}
