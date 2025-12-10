package fr.utilix.eshop.api.exposition.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequestDTO(

        @Valid
        @NotBlank(message = "Le quantité ne peut pas être vide.")
        @NotNull(message = "Le quantité est obligatoire.")
        Integer quantity,

        @Valid
        @NotNull(message = "L'id produit est obligatoire.")
        Long productId
) {
}
