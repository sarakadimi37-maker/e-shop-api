package fr.utilix.eshop.api.mappers;

import fr.utilix.eshop.api.exposition.dtos.request.ProductRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.ProductResponseDTO;
import fr.utilix.eshop.api.persistence.entities.ProductEntity;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

public class ProductMapper {
    private ProductMapper() {
    }

    // 👇 Convertit un ProductRequestDTO en ProductEntity
    public static ProductEntity toEntity(ProductRequestDTO dto) {
        ProductEntity entity = new ProductEntity();
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setImageUrl(dto.imageUrl());
        entity.setActive(dto.isActive()); // valeur par défaut
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
                entity.getStock() > 0,
                entity.getDiscount(),
                entity.getDiscount() > 0,
                entity.getCreatedAt().plus(7, ChronoUnit.DAYS).isBefore(Instant.now()),
                entity.getCategorie() != null ? entity.getCategorie().getLabel() : "",
                entity.getRating()
        );
    }
}