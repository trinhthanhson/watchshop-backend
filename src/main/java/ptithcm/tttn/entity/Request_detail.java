package ptithcm.tttn.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "request_detail")
public class Request_detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long request_detail_id;

    @Column
    private int quantity;

    @Column
    private int price;

    @Column
    private Long request_id;

    @Column
    private String product_id;

    @ManyToOne
    @JoinColumn(name = "request_id",insertable = false,updatable = false)
    private Transaction_request request;

    @ManyToOne
    @JoinColumn(name = "product_id",insertable = false,updatable = false)
    private Product product_request;

}
