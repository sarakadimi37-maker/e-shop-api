package fr.utilix.eshop.api.persistence.repositories;

import fr.utilix.eshop.api.enumeration.OrderStatus;
import fr.utilix.eshop.api.persistence.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query("select o from OrderEntity o where o.customer.id = :customerId and o.status = :status")
    Optional<OrderEntity> findByCustomerAndStatus(
            @Param("customerId") Long customerId, @Param("status") OrderStatus status);

}
