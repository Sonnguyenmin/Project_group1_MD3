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
public class Payments {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer paymentId;
    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    private Double money;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users users;
}
