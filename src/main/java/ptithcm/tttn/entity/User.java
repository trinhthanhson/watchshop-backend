package ptithcm.tttn.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long user_id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String status;

    @Column
    private String access_token;

    @Column
    private String expired_token;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Column
    private LocalDateTime expired_date;

    @Column
    private float points;

    @Column
    private Long role_id;

    @Column
    private Long updated_by;

    @ManyToOne
    @JoinColumn(name = "role_id",insertable = false, updatable = false)
    private Role role_user;

    @OneToOne(mappedBy = "user_customer")
    @JsonIgnore
    private Customer customer;

    @OneToOne(mappedBy = "user_staff")
    @JsonIgnore
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "updated_by",insertable = false, updatable = false)
    @JsonIgnore
    private Staff staff_update;





}
