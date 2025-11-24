package fr.utilix.eshop_api.persistence.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "favorite")
public class FavoriteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

}
