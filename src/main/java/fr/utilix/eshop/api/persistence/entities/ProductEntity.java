package fr.utilix.eshop.api.persistence.entities;

import fr.utilix.eshop.api.exposition.dtos.request.ProductRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product",
        indexes = {
                @Index(name = "idx_product_name", columnList = "name"),
                @Index(name = "idx_product_price", columnList = "price")
        })
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductEntity extends BaseEntity{


    public void updateFrom(ProductRequestDTO dto){
        this.name = dto.name();
        this.description = dto.description();
        this.price = dto.price();
        this.stock = dto.stock();
        this.discount = dto.discount();
        this.isActive = dto.isActive();
    }

    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "discount", nullable = false)
    private double discount;

    @Column(name = "rating", nullable = false)
    private double rating;

    @Column(name = "promo_start")
    private LocalDate promoStart;

    @Column(name = "promo_end")
    private LocalDate promoEnd;

    @ManyToOne
    private CategoryEntity categorie;

}
