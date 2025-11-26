package fr.utilix.eshop.api.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddressEntity extends BaseEntity{

    @Column(name = "street", nullable = false, length = 120)
    private String street;

    @Column(name = "city", nullable = false, length = 80)
    private String city;

    @Column(name = "zip_code", nullable = false, length = 10)
    private String zipCode;

    @Column(name = "country", nullable = false, length = 80)
    private String country;

    @OneToOne(mappedBy = "address")
    private CustomerEntity customer;
}
