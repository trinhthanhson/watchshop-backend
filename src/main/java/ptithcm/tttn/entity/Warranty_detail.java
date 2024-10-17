package ptithcm.tttn.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "warranty_detail")
public class Warranty_detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long warranty_detail_id;

    @Column
    private String content;

    @Column
    private Long order_detail_id;

    @Column
    private Long warranty_id;

    @ManyToOne
    @JoinColumn(name = "order_detail_id",insertable = false,updatable = false)
    private Order_detail warranty_order_detail;

    @ManyToOne
    @JoinColumn(name = "warranty_id",insertable = false,updatable = false)
    private Warranty warranty_detail;
}
