package fr.utilix.eshop.api.persistence.repositories;

import fr.utilix.eshop.api.persistence.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

}
