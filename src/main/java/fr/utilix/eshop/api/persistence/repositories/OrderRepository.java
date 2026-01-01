package fr.utilix.eshop.api.persistence.repositories;

import fr.utilix.eshop.api.enumeration.OrderStatus;
import fr.utilix.eshop.api.persistence.entities.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query("select o from OrderEntity o where o.customer.id = :customerId and o.status = :status")
    Optional<OrderEntity> findByCustomerAndStatus(
            @Param("customerId") Long customerId, @Param("status") OrderStatus status);


    Page<OrderEntity> findAllByCustomerId(Pageable pageable, Long customerId);

    @Query("select o from OrderEntity o inner join fetch o.customer c inner join fetch c.address left join fetch o.orderItems")
    List<OrderEntity> findAllFetch();
}
