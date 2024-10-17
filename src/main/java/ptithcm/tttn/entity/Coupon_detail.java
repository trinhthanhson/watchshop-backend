package ptithcm.tttn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "coupon_detail")
public class Coupon_detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long coupon_detail_id;

    @Column
    private float percent;

    @Column
    private String status;

    @Column
    private Long coupon_id;

    @Column
    private String product_id;

    @ManyToOne
    @JoinColumn(name = "coupon_id",insertable = false,updatable = false)
    @JsonIgnore
    private Coupon coupon_detail;

    @ManyToOne
    @JoinColumn(name = "product_id",insertable = false,updatable = false)
    private Product product_coupon;

}
