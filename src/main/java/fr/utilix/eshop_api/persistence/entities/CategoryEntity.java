package fr.utilix.eshop_api.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryEntity extends BaseEntity{

    @Column(name = "label", nullable = false, length = 100)
    private String label;

}
