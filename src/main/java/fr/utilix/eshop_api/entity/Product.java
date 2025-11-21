package fr.utilix.eshop_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product extends BaseEntity{

    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "Image_url", nullable = false, length = 255)
    private String ImageUrl;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "discount", nullable = false)
    private double discount;


    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;
}
