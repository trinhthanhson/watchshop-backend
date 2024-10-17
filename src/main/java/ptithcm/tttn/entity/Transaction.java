package ptithcm.tttn.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long transaction_id;

    @Column
    private String note;

    @Column
    private LocalDateTime created_at;

    @Column
    private int total_quantity;

    @Column
    private int total_price;

    @Column
    private Long type_id;

    @Column
    private Long supplier_id;

    @Column
    private Long staff_id;

    @ManyToOne
    @JoinColumn(name = "type_id",insertable = false,updatable = false)
    private Type type_transaction;

    @ManyToOne
    @JoinColumn(name = "supplier_id",insertable = false,updatable = false)
    private Supplier supplier_transaction;

    @ManyToOne
    @JoinColumn(name = "staff_id",insertable = false,updatable = false)
    private Staff staff_transaction;

    @OneToMany(mappedBy = "transaction_detail")
    private List<Transaction_detail> transactionDetails;



}
