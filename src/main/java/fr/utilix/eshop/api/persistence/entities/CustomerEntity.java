package fr.utilix.eshop.api.persistence.entities;

import fr.utilix.eshop.api.exposition.dtos.request.CustomerRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerEntity extends BaseEntity{

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "phone", nullable = false, length = 50)
    private String phone;

    // Métier : le Customer est parent de Order (il possède plusieurs commandes). => list
    @OneToMany(mappedBy = "customer",
    cascade = CascadeType.ALL,
    orphanRemoval = true
    )
    private List<OrderEntity> orders;


    @OneToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true
    )
    @JoinColumn(name = "address_id")
    private AddressEntity address;


    @OneToMany(mappedBy = "customer",
    cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true
    )
    private List<FavoriteEntity> favorites;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


    public void updateForm(CustomerRequestDTO dto) {
    }
}

