package ptithcm.tttn.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long bill_id;

    @Column
    private LocalDateTime created_at;

    @Column
    private Long staff_id;

    @Column
    private Long order_id;


    @ManyToOne
    @JoinColumn(name = "staff_id",insertable = false,updatable = false)
    private Staff staff_bill;

    @OneToOne
    @JoinColumn(name = "order_id",insertable = false,updatable = false)
    private Orders order_bill;

}
