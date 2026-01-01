package fr.utilix.eshop.api.persistence.entities;

import fr.utilix.eshop.api.enumeration.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "orders",
    indexes = {
        @Index(name = "idx_orders_status", columnList = "status")
    }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderEntity extends BaseEntity{

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // Technique : c’est pourtant Order le propriétaire de la relation, car c’est lui qui a la colonne customer_id dans sa table.
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @OneToMany(mappedBy = "order")
    private List<OrderItemEntity> orderItems;
}
