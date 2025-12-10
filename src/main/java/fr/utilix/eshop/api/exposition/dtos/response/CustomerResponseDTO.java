package fr.utilix.eshop.api.exposition.dtos.response;

import java.util.List;

/**
 * Ce DTO représente ce que le backend renvoie au front (ce qu’il répond).
 */
public record CustomerResponseDTO(
        Long id,
        String firstName,
        String lastName,
        AddressResponseDTO address,
        List<OrderResponseDTO> orders,
        List<FavoriteResponseDTO> favorites
) {}
