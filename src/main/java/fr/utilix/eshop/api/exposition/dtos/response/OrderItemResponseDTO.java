package fr.utilix.eshop.api.exposition.dtos.response;

public record OrderItemResponseDTO(

        Long orderItemId,
         Integer quantity,
         Double uintPrice,
         ProductResponseDTO product
) {}
