package ptithcm.tttn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long coupon_id;

    @Column
    private Date start_date;

    @Column
    private Date end_date;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Column
    private String status;

    @Column
    private String content;

    @Column
    private String title;

    @Column
    private Long created_by;

    @Column
    private Long updated_by;

    @OneToMany(mappedBy = "coupon_detail")
    private List<Coupon_detail> couponDetails;

    @ManyToOne
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private Staff staff_create;

    @ManyToOne
    @JoinColumn(name = "updated_by", insertable = false, updatable = false)
    private Staff staff_update;

}
