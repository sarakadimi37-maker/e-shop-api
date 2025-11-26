package fr.utilix.eshop.api.persistence.repositories;

import fr.utilix.eshop.api.persistence.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
