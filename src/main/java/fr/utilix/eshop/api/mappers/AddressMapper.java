package fr.utilix.eshop.api.mappers;

import fr.utilix.eshop.api.exposition.dtos.request.AddressRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.AddressResponseDTO;
import fr.utilix.eshop.api.persistence.entities.AddressEntity;


public class AddressMapper {

    public static AddressEntity toEntity(AddressRequestDTO dto) {
        if(dto == null) {
            return null;
        }
        AddressEntity entity = new AddressEntity();
        entity.setCity(dto.city());
        entity.setStreet(dto.street());
        entity.setZipCode(dto.zipCode());
        entity.setCountry(dto.country());
        return entity;
    }

    public static AddressResponseDTO toDto(AddressEntity entity){
        return new AddressResponseDTO(
                entity.getId(),
                entity.getCity(),
                entity.getStreet(),
                entity.getZipCode(),
                entity.getCountry()
                );
    }
}
