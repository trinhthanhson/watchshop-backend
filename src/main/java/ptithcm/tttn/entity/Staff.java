package ptithcm.tttn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long staff_id;

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
    private int salary;

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
    @JsonIgnore
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    private User user_staff;

    @OneToMany(mappedBy = "staff_bill")
    @JsonIgnore
    private List<Bill> bills;

    @OneToMany(mappedBy = "staff_update")
    @JsonIgnore
    private List<User> user_update;

    @OneToMany(mappedBy = "staff_warranty")
    @JsonIgnore
    private List<Warranty> warranties;

    @OneToMany(mappedBy = "staff_order")
    @JsonIgnore
    private List<Orders> orders;

    @OneToMany(mappedBy = "staff_created")
    @JsonIgnore
    private List<Product> products_create;

    @OneToMany(mappedBy = "staff_updated")
    @JsonIgnore
    private List<Product> products_updated;


    @OneToMany(mappedBy = "staff_update")
    @JsonIgnore
    private List<Orders> order_update;

    @OneToMany(mappedBy = "price_created")
    @JsonIgnore
    private List<Update_price> createPrices;

    @OneToMany(mappedBy = "price_updated")
    @JsonIgnore
    private List<Update_price> updatePrices;

    @OneToMany(mappedBy = "staff_create")
    @JsonIgnore
    private List<Coupon> coupon_create;

    @OneToMany(mappedBy = "staff_update")
    @JsonIgnore
    private List<Coupon> coupon_update;

    @OneToMany(mappedBy = "staff_created_request")
    @JsonIgnore
    private List<Transaction_request> transactionRequestsCreate;

    @OneToMany(mappedBy = "staff_updated_request")
    @JsonIgnore
    private List<Transaction_request> transactionRequestsUpdate;

    @OneToMany(mappedBy = "staff_transaction")
    @JsonIgnore
    private List<Transaction> Transactions;

    @OneToMany(mappedBy = "staff_create")
    @JsonIgnore
    private List<Category> category_create;

    @OneToMany(mappedBy = "staff_update")
    @JsonIgnore
    private List<Category> category_update;

    @OneToMany(mappedBy = "staff_create")
    @JsonIgnore
    private List<Brand> brand_create;

    @OneToMany(mappedBy = "staff_update")
    @JsonIgnore
    private List<Brand> brand_update;


}
