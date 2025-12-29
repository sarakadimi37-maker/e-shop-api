package fr.utilix.eshop.api.persistence.repositories;

import fr.utilix.eshop.api.persistence.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByNameContainingIgnoreCase(String keyword);

    Page<ProductEntity> findAll(Pageable pageable);

    @Query("select p from ProductEntity p where p.discount > 0")
    List<ProductEntity> findPromotion(Pageable pageable);
}
