package fr.utilix.eshop.api.exposition.dtos;

/**
 * Ce DTO représente ce que le client envoie (lors d’un POST ou PUT).
 * @param name
 * @param description
 * @param imageUrl
 * @param isActive
 * @param price
 * @param stock
 * @param discount
 */
public record ProductRequestDTO(
        String name,
        String description,
        String imageUrl,
        Boolean isActive,
        double price,
        int stock,
        double discount
) {}
