package fr.utilix.eshop.api.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

  /* ak
    @OneToMany(mappedBy = "customer")
    private Set<Address> address;
*/

    // Métier : le Customer est parent de Order (il possède plusieurs commandes). => list
    @OneToMany(mappedBy = "customer",
    cascade = CascadeType.ALL,
    orphanRemoval = true
    )
    private List<OrderEntity> orders = new ArrayList<>();


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
    private Set<FavoriteEntity> favorites;



}

