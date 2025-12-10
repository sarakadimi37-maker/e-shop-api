package fr.utilix.eshop.api.exposition.dtos.response;

import fr.utilix.eshop.api.persistence.entities.ProductEntity;

public record FavoriteResponseDTO(
         Long id,
         ProductEntity product
) {
}
