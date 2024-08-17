package ra.nhom1_watchingfilmonline.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content="";
    @Column(nullable = false)
    private Integer rating;
    @ManyToOne
    @JoinColumn(name ="userId")
    private Users users;

    @ManyToOne
    @JoinColumn (name = "filmId")
    private Films films;

}
