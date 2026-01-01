package fr.utilix.eshop.api.exposition.dtos.request;

import fr.utilix.eshop.api.enumeration.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;


public record OrderRequestDTO(

        @Schema(description = "le status de la commande")
        OrderStatus status,

        @Schema(description = "L'identifiant de l'id produit")
        OrderItemRequestDTO item


) {
}
