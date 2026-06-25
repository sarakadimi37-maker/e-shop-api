package fr.utilix.eshop.api.mappers;

import fr.utilix.eshop.api.exposition.dtos.request.CustomerRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.CustomerResponseDTO;
import fr.utilix.eshop.api.persistence.entities.CustomerEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class CustomerMapper {

    public static CustomerEntity toEntity(CustomerRequestDTO dto){

        CustomerEntity entity = new CustomerEntity();
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setPhone(dto.phone());
        return entity;
    }

    public static CustomerResponseDTO toDto(CustomerEntity entity){
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
