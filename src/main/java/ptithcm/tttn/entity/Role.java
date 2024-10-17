package ptithcm.tttn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long role_id;

    @Column
    private String role_name;

    @Column
    private LocalDateTime created_at;

    @OneToMany(mappedBy = "role_user")
    @JsonIgnore
    private List<User> Users;
}
