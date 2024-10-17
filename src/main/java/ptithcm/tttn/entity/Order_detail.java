package ptithcm.tttn.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@Table(name = "order_detail")
public class Order_detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long order_detail_id;

    @Column
    private int quantity;

    @Column
    private int price;

    @Column
    private String product_id;

    @Column
    private Long order_id;

    @OneToMany(mappedBy = "warranty_order_detail")
    private List<Warranty_detail> warrantyDetails;

    @ManyToOne
    @JoinColumn(name = "product_id",insertable = false,updatable = false)
    private Product product_order;

    @ManyToOne
    @JoinColumn(name = "order_id",insertable = false,updatable = false)
    private Orders orders;

    @OneToOne(mappedBy = "order_detail")
    private Review review;


}
