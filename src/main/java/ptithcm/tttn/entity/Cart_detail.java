package ptithcm.tttn.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "cart_detail")
public class Cart_detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long cart_detail_id;

    @Column
    private int quantity;

    @Column
    private Long customer_id;

    @Column
    private String product_id;

    @ManyToOne
    @JoinColumn(name = "customer_id",insertable = false,updatable = false)
    private Customer customer_cart;

    @ManyToOne
    @JoinColumn(name = "product_id",insertable = false,updatable = false)
    private Product product_cart;

}
