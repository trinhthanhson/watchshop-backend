package ptithcm.tttn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column
    private String product_id;

    @Column
    private String product_name;

    @Column
    private int quantity;

    @Column
    private String color;

    @Column
    private String band_material;

    @Column
    private String dial_type;

    @Column
    private String func;

    @Column
    private String gender;

    @Column
    private String model;

    @Column
    private String series;

    @Column
    private String water_resistance;

    @Column
    private String case_diameter;

    @Column
    private String case_material;

    @Column
    private String detail;

    @Column
    private String image;

    @Column
    private String machine_movement;

    @Column
    private String band_width;

    @Column
    private String case_thickness;

    @Column
    private String status;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Column
    private Long category_id;

    @Column
    private Long brand_id;

    @Column
    private Long created_by;

    @Column
    private Long updated_by;

    @ManyToOne
    @JoinColumn(name = "category_id",insertable = false,updatable = false)
    private Category category_product;

    @ManyToOne
    @JoinColumn(name = "created_by",insertable = false,updatable = false)
    private Staff staff_created;

    @ManyToOne
    @JoinColumn(name = "updated_by",insertable = false,updatable = false)
    private Staff staff_updated;

    @ManyToOne
    @JoinColumn(name = "brand_id",insertable = false,updatable = false)
    private Brand brand_product;

    @JsonIgnore
    @OneToMany(mappedBy = "product_order")
    private List<Order_detail> orderDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "product_cart")
    private List<Cart_detail> cartDetails;

    @OneToMany(mappedBy = "product_price")
    private List<Update_price> updatePrices;

    @JsonIgnore
    @OneToMany(mappedBy = "product_coupon")
    private List<Coupon_detail> couponDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "product_request")
    private List<Request_detail> requestDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "product_transaction_detail")
    private List<Transaction_detail> transactionDetails;

    @OneToMany(mappedBy = "product_review")
    private List<Review> reviews;


}
