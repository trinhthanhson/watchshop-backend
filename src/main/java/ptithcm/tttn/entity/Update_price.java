package ptithcm.tttn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "update_price")
public class Update_price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long update_price_id;

    @Column
    private int price_old;

    @Column
    private int price_new;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Column
    private int original_price;

    @Column
    private Long created_by;

    @Column
    private Long updated_by;

    @Column
    private String product_id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "created_by",insertable = false,updatable = false)
    private Staff price_created;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "updated_by",insertable = false,updatable = false)
    private Staff price_updated;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id",insertable = false,updatable = false)
    private Product product_price;
}
