package fr.utilix.eshop.api.mappers;

import fr.utilix.eshop.api.exposition.dtos.request.CustomerRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.CustomerResponseDTO;
import fr.utilix.eshop.api.persistence.entities.AddressEntity;
import fr.utilix.eshop.api.persistence.entities.CustomerEntity;
import fr.utilix.eshop.api.persistence.entities.UserEntity;
import fr.utilix.eshop.api.persistence.repositories.AddressRepository;
import fr.utilix.eshop.api.persistence.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class CustomerMapper {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;


    public CustomerEntity toEntity(CustomerRequestDTO dto){

        CustomerEntity entity = new CustomerEntity();
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        AddressEntity address = addressRepository.findById(dto.addressId())
                .orElseThrow(() -> new RuntimeException("Address introuvable"));

        entity.setAddress(address);
        UserEntity user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("User intouvable"));
        entity.setUser(user);

        return entity;
    }

    public CustomerResponseDTO toDto(CustomerEntity entity){
        return new CustomerResponseDTO(
           entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                AddressMapper.toDto(entity.getAddress()),
                new ArrayList<>(),// todo
                new ArrayList<>()
        );
    }
}
