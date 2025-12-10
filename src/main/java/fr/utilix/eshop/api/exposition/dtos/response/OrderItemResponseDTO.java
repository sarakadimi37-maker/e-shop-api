package fr.utilix.eshop.api.exposition.dtos.response;

public record OrderItemResponseDTO(

         Integer quantity,
         Double uintPrice,
         ProductResponseDTO product
) {}
