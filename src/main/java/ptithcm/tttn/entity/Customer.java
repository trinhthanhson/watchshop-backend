package ptithcm.tttn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long customer_id;

    @Column
    private String citizen_id;

    @Column
    private String first_name;

    @Column
    private String last_name;

    @Column
    private LocalDateTime birthday;

    @Column
    private String gender;

    @Column
    private String tax_id;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Column
    private Long user_id;

    @OneToOne
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    private User user_customer;

    @OneToMany(mappedBy = "customer_order")
    @JsonIgnore
    private List<Orders> orders;

    @OneToMany(mappedBy = "customer_cart")
    @JsonIgnore
    private List<Cart_detail> cartDetails;

    @OneToMany(mappedBy = "review_created")
    @JsonIgnore
    private List<Review> reviews_created;

    @OneToMany(mappedBy = "review_update")
    @JsonIgnore
    private List<Review> reviews_updated;

}
