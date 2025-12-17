package fr.utilix.eshop.api.persistence.repositories;

import fr.utilix.eshop.api.persistence.entities.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

    @Query("""
            select oi from OrderItemEntity oi
            where oi.order.id = :orderId
            and oi.product.id = :productId
            """  )
    Optional<OrderItemEntity> getByOrderAndProduct(
            @Param("orderId") Long orderId, @Param("productId") Long productId);
}
