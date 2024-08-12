package ra.nhom1_watchingfilmonline.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Roles {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer roleId;

    private String roleName;
}
