package fr.utilix.eshop.api.exposition.dtos.request;

import fr.utilix.eshop.api.persistence.entities.ProductEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record FavoriteRequestDTO(

        @Valid
        Long productId,
        Long customerId
) {


}
