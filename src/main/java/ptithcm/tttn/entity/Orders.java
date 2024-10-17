package ptithcm.tttn.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long order_id;

    @Column
    private String recipient_name;

    @Column
    private String recipient_phone;

    @Column
    private int total_quantity;

    @Column
    private int total_price;

    @Column
    private String address;

    @Column
    private String note;

    @Column
    private String status;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Column
    private Long staff_id;

    @Column Long updated_by;

    @Column
    private Long customer_id;

    @OneToOne(mappedBy = "order_bill")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "staff_id",insertable = false,updatable = false)
    private Staff staff_order;

    @ManyToOne
    @JoinColumn(name = "updated_by", insertable = false, updatable = false)
    private Staff staff_update;

    @ManyToOne
    @JoinColumn(name = "customer_id",insertable = false,updatable = false)
    private Customer customer_order;

    @OneToMany(mappedBy = "orders")
    private List<Order_detail> orderDetails;


}
