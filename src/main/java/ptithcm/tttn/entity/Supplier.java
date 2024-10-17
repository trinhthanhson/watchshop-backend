package ptithcm.tttn.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long supplier_id;

    @Column
    private String supplier_name;

    @Column
    private String tax_id;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String fax;

    @OneToMany(mappedBy = "supplier_transaction")
    private List<Transaction> Transactions;
}
