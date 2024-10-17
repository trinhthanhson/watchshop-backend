package ptithcm.tttn.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
@Table(name = "warranty")
public class Warranty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long warranty_id;

    @Column
    private LocalDateTime created_at;

    @Column
    private String status;

    @Column
    private Long staff_id;

    @ManyToOne
    @JoinColumn(name = "staff_id",insertable = false,updatable = false)
    private Staff staff_warranty;

    @OneToMany(mappedBy = "warranty_detail")
    private List<Warranty_detail> warrantyDetails;


}
