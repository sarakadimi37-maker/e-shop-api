package fr.utilix.eshop.api.mappers;

import fr.utilix.eshop.api.exposition.dtos.request.ProductRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.ProductResponseDTO;
import fr.utilix.eshop.api.persistence.entities.ProductEntity;

public class ProductMapper {

    // 👇 Convertit un ProductRequestDTO en ProductEntity
    public static ProductEntity toEntity(ProductRequestDTO dto) {
        ProductEntity entity = new ProductEntity();
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setImageUrl(dto.imageUrl());
        entity.setActive(dto.isActive() != null ? dto.isActive() : true); // valeur par défaut
        entity.setPrice(dto.price());
        entity.setStock(dto.stock());
        entity.setDiscount(dto.discount());
        return entity;
    }

    // 👇 Convertit un ProductEntity en ProductResponseDTO
    public static ProductResponseDTO toDto(ProductEntity entity) {
        return new ProductResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getImageUrl(),
                entity.getPrice(),
                entity.getStock(),
                entity.getDiscount()
        );
    }
}