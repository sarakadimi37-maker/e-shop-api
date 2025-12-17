package fr.utilix.eshop.api.persistence.repositories;

import fr.utilix.eshop.api.persistence.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    // @Query("from CustomerEntity c where lower(c.firstName) = lower(:word) ")
    List<CustomerEntity> findByFirstNameContainingIgnoreCase(/*@Param("word")*/ String keyword);
}
