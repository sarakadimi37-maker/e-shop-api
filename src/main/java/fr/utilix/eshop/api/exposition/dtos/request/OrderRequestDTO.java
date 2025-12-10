package fr.utilix.eshop.api.exposition.dtos.request;

import fr.utilix.eshop.api.enumeration.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record OrderRequestDTO(

        @Schema(description = "le status de la commande")
        OrderStatus status,

        @Schema(description = "L'identifiant de l'id produit")
        @Valid
        @NotNull(message = "le produit doit avir un id")
        Long productId,

        @Valid
        @NotNull(message = "La quantité doit être > 0.")
        Integer quantity


) {
}
