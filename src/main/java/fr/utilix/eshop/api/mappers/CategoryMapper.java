package fr.utilix.eshop.api.mappers;

import fr.utilix.eshop.api.exposition.dtos.request.CategoryRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.CategoryResponseDTO;
import fr.utilix.eshop.api.persistence.entities.CategoryEntity;

public class CategoryMapper {

    public static CategoryEntity toEntity(CategoryRequestDTO dto){
        CategoryEntity entity = new CategoryEntity();
        entity.setLabel(dto.label());
        return entity;
    }

    public static CategoryResponseDTO toDto(CategoryEntity entity){
        return new CategoryResponseDTO(
            entity.getId(),
                entity.getLabel()
        );
    }


}
